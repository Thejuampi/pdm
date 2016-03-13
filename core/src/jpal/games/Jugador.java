package jpal.games;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;

import jpal.games.gestor.Constantes;
import jpal.games.gestor.GestorCamara;
import jpal.games.gestor.GestorSprite;
import jpal.games.pantalla.Moneda;
import jpal.games.pantalla.Pantalla;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**
 * Created by juan on 24/02/16.
 */
public class Jugador implements InputProcessor {

    private final float radioPelota;

    private CircleShape forma;

    private FixtureDef fixtureDef;

    private BodyDef bodyDef;

    private Body body;

    private Sprite sprite;

    GestorCamara gestorCamara;

    private Pantalla pantalla;

    private boolean izq, der, arr, aba;

    private Integer puntaje = 0;

    /**
     * Referencia al mundo, no se si es necesario utilizar por el momento
     */
    private World mundo;

    private int vidas = 1; // arranca con 1 vida

//    private static GestorTextura gestorTextura = GestorTextura.get();

    public void update() {
        sprite.setPosition(body.getPosition().x, body.getPosition().x);
    }

    public Jugador(final Pantalla pantalla) {
        gestorCamara = GestorCamara.get();
        this.radioPelota = 0.5f;
        this.izq = false;
        this.der = false;
        this.arr = false;
        this.aba = false;

        this.sprite = new Sprite(GestorSprite.get().pelota);
        this.mundo = pantalla.getMundo();
        this.pantalla = pantalla;
        this.bodyDef = new BodyDef();
        bodyDef.position.set(3.0f, radioPelota * 10);
        bodyDef.fixedRotation = true; // para que no rote?
        this.bodyDef.type = BodyDef.BodyType.DynamicBody; // el jugador es un cuerpo dinamico (interactua fisicamente con el mundo)
        this.body = mundo.createBody(bodyDef);

        this.forma = new CircleShape();
        this.forma.setRadius(radioPelota);

        this.fixtureDef = new FixtureDef();
//        this.fixtureDef.restitution = 1.0f;
        this.fixtureDef.shape = forma;
        body.createFixture(forma, 0.5f).setUserData(this);

        forma.dispose();

        mundo.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fa = contact.getFixtureA();
                Fixture fb = contact.getFixtureB();
                Fixture jugador = null;
                //reviso que se puedan usar los datos sin que lanze una excepcion
                if (revisarFixtures(fa, fb)) return;
                if (intentarGanarOPerder(fa, fb, pantalla)) return;
                if (intentarJuntarMoneda(fa, fb, pantalla)) return;

                //Si no es nada de lo anterior, entonces el jugador debe rebotar
                calcularRebote(contact, fa, fb, jugador);
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
//                Gdx.app.log("", impulse.toString());
            }
        });

    }

    private boolean revisarFixtures(Fixture fa, Fixture fb) {
        if (fa == null || fb == null || (fa.getUserData() == null && fb.getUserData() == null))
            return true;
        return false;
    }

    private boolean intentarGanarOPerder(Fixture fa, Fixture fb, Pantalla pantalla) {
        if (Constantes.GANAR_ID.equals(fb.getUserData()) || Constantes.GANAR_ID.equals(fa.getUserData())) {
            pantalla.accionAlGanar();
            return true;
        } else if (Constantes.PERDER_ID.equals(fb.getUserData()) || Constantes.PERDER_ID.equals(fa.getUserData())) {
            pantalla.accionAlPerder();
            return true;
        }
        return false;
    }

    private boolean intentarJuntarMoneda(Fixture fa, Fixture fb, Pantalla pantalla) {
        if (fa.getUserData() instanceof Moneda) {
            juntarMoneda(fa, pantalla);
            return true;
        }

        if (fb.getUserData() instanceof Moneda) {
            juntarMoneda(fb, pantalla);
            return true;
        }
        return false;
    }

    private void calcularRebote(Contact contact, Fixture fa, Fixture fb, Fixture jugador) {
        if (fa.getUserData() == Jugador.this) {
            jugador = fa;
        } else if (fb.getUserData() == Jugador.this) {
            jugador = fb;
        }
        WorldManifold worldManifold = contact.getWorldManifold();

        // http://www.iteramos.com/pregunta/32746/como-calcular-el-angulo-de-rebote
        Vector2 v = jugador.getBody().getLinearVelocity();
        Vector2 n = worldManifold.getNormal();
        Vector2 u = n.scl(v.dot(n) / n.dot(n));
        Vector2 w = new Vector2(v.x - u.x, v.y - u.y);  //v - u;

        Vector2 nv = w.add(u.scl(-1.0f));
        nv.scl(0.7f); // como la restitucion no me da bola, la seteo a pata
        jugador.getBody().setLinearVelocity(nv);
    }

    private void juntarMoneda(Fixture fixture, Pantalla pantalla) {
        Moneda moneda = (Moneda) fixture.getUserData();
        fixture.setUserData(null);
        pantalla.paraEliminar.add(moneda.getCuerpo());
        TiledMapTileLayer layer = (TiledMapTileLayer) pantalla.getMapaTiled().getLayers().get("colectables");
        layer.setCell((int) moneda.x, (int) moneda.y, new Cell());

        puntaje += moneda.getPuntaje();
        if (puntaje % 20 == 0) {
            vidas += 1;
        }
    }


    public Vector2 getPosicion() {
        return body.getPosition();
    }

    public void moverPorOrientacion(float orientacion) {
        orientacion = orientacion / 2.0f;
        Vector2 velocidad = body.getLinearVelocity();
        velocidad.set((velocidad.x + orientacion) / 2.0f, velocidad.y);
        body.setLinearVelocity(velocidad);
    }

    /**
     * Solamente impulsa si la pelota esta rebotando para arriba
     *
     * @param magnitud magnitud al cuadrado de la aceleración
     */
    public void impulsar(float magnitud) {
        Vector2 vel = body.getLinearVelocity();
        if (vel.y <= 10.0f && vel.y >= -0.1f) {
            body.applyLinearImpulse(0.0f, magnitud / 500.0f, 0.0f, 0.0f, true);
        }
    }

    public void agregarVida() {
        this.vidas += 1;
    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.LEFT:
                izq = true;
                break;
            case Input.Keys.RIGHT:
                der = true;
                break;
            case Input.Keys.UP:
                arr = true;
                break;
            case Input.Keys.DOWN:
                aba = true;
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                izq = false;
                break;
            case Input.Keys.RIGHT:
                der = false;
                break;
            case Input.Keys.UP:
                arr = false;
                break;
            case Input.Keys.DOWN:
                aba = false;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(sprite, this.body.getPosition().x, this.body.getPosition().y, 128f, 128f);
    }
}
