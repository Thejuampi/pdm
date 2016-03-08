package jpal.games;

import com.badlogic.gdx.Gdx;
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

import jpal.games.gestor.GestorCamara;
import jpal.games.pantalla.Pantalla;

/**
 * Created by juan on 24/02/16.
 */
public class Jugador {

    private final float radioPelota;

    private CircleShape forma;

    private FixtureDef fixtureDef;

    private BodyDef bodyDef;

    private Body body;

    GestorCamara gestorCamara;

    /**
     * Referencia al mundo, no se si es necesario utilizar por el momento
     */
    private World mundo;

//    private static GestorTextura gestorTextura = GestorTextura.get();

    public Jugador(Pantalla pantalla) {
        gestorCamara = GestorCamara.get();
//        this.sprite = new Sprite(gestorTextura.pelotaJugador);
        this.forma = new CircleShape();
        this.radioPelota = 0.5f;
        this.forma.setRadius(radioPelota);

        //Definición de propiedades físicas
        this.fixtureDef = new FixtureDef();
        this.fixtureDef.shape = forma;
        this.fixtureDef.density = 0.5f;
        this.fixtureDef.friction = 0.4f;
        this.fixtureDef.restitution = 0.6f;

        this.bodyDef = new BodyDef();

        bodyDef.position.set(0.0f, radioPelota * 5);
        bodyDef.fixedRotation = true; // para que no rote?
        this.bodyDef.type = BodyDef.BodyType.DynamicBody; // el jugador es un cuerpo dinamico (interactua fisicamente con el mundo)

        this.mundo = pantalla.getMundo();
        this.body = mundo.createBody(bodyDef);

        body.createFixture(forma, 0.5f).setUserData(this);
        forma.dispose(); // ????

        body.setLinearVelocity(new Vector2(1.0f, 0.0f));

        mundo.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fa = contact.getFixtureA();
                Fixture fb = contact.getFixtureB();
                Fixture jugador = null;
                if (fa == null || fb == null) return;
                if (fa.getUserData() == Jugador.this) {
                    jugador = fa;
                } else if (fb.getUserData() == Jugador.this) {
                    jugador = fb;
                }
                WorldManifold worldManifold = contact.getWorldManifold();

                //TODO (juan) ver como se puede mejorar esto.
                // http://www.iteramos.com/pregunta/32746/como-calcular-el-angulo-de-rebote
                Vector2 v = jugador.getBody().getLinearVelocity();
                Vector2 n = worldManifold.getNormal();
                Vector2 u =   n.scl(v.dot(n) / n.dot(n));
                Vector2 w = new Vector2(v.x - u.x, v.y - u.y);  //v - u;

                Vector2 nv = w.add(u.scl(-1.0f));
                jugador.getBody().setLinearVelocity(nv);
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                Gdx.app.log("", impulse.toString());
            }
        });

    }


    public Vector2 getPosicion() {
        return body.getPosition();
    }

    public void moverPorOrientacion(float orientacion) {

        orientacion = orientacion / 10.0f;

        Vector2 velocidad = body.getLinearVelocity();
        velocidad.set((velocidad.x + orientacion) / 2.0f, velocidad.y);
        body.setLinearVelocity(velocidad);

    }
}
