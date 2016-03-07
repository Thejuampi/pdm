package jpal.games;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

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
        this.forma.setRadius(radioPelota); // (TODO ver el radio de la pelota);

        //Definición de propiedades físicas
        this.fixtureDef = new FixtureDef();
        this.fixtureDef.shape = forma;
        this.fixtureDef.density = 0.5f;
        this.fixtureDef.friction = 0.4f;
        this.fixtureDef.restitution = 0.6f;

        this.bodyDef = new BodyDef();

        bodyDef.position.set(0.0f,radioPelota*2);
        bodyDef.fixedRotation = true; // para que no rote?
        this.bodyDef.type = BodyDef.BodyType.DynamicBody; // el jugador es un cuerpo dinamico (interactua fisicamente con el mundo)

        this.mundo = pantalla.getMundo();
        this.body = mundo.createBody(bodyDef);

        Fixture fixture = body.createFixture(forma, 0.5f);
        forma.dispose(); // ????
    }

    public Vector2 getPosicion() {
        return body.getPosition();
    }

}
