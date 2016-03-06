package jpal.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import jpal.games.gestor.GestorCamara;
import jpal.games.gestor.GestorTextura;

/**
 * Created by juan on 24/02/16.
 */
public class Jugador {

//    private Sprite sprite;

    private CircleShape forma;

    private FixtureDef formaFixtureDef;

    private BodyDef bodyDef;

    private Body body;

    GestorCamara gestorCamara;

    /**
     * Referencia al mundo, no se si es necesario utilizar por el momento
     */
    private World mundo;

//    private static GestorTextura gestorTextura = GestorTextura.get();

    public Jugador(World mundo) {
        gestorCamara = GestorCamara.get();
//        this.sprite = new Sprite(gestorTextura.pelotaJugador);
        this.forma = new CircleShape();
        this.forma.setRadius(0.5f); // (TODO ver el radio de la pelota);

        //Definición de propiedades físicas
        this.formaFixtureDef = new FixtureDef();
        this.formaFixtureDef.shape = forma;
        this.formaFixtureDef.density = 0.5f;
        this.formaFixtureDef.friction = 0.4f;
        this.formaFixtureDef.restitution = 0.6f;

        this.bodyDef = new BodyDef();


        bodyDef.position.set(0.0f,0.0f);
        bodyDef.fixedRotation = true; // para que no rote?
        this.bodyDef.type = BodyDef.BodyType.DynamicBody; // el jugador es un cuerpo dinamico (interactua fisicamente con el mundo)

        this.mundo = mundo;
        this.body = mundo.createBody(bodyDef);

        Fixture fixture = body.createFixture(formaFixtureDef);
        forma.dispose(); // ????

    }

}
