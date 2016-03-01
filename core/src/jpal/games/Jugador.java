package jpal.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import jpal.games.gestor.GestorTextura;

/**
 * Created by juan on 24/02/16.
 */
public class Jugador implements InputProcessor {

    private Sprite sprite;

    private CircleShape forma;

    private FixtureDef formaFixtureDef;

    private BodyDef bodyDef;

    private World mundo;

    private static GestorTextura gestorTextura = GestorTextura.get();

    public Jugador(World mundo) {
        this.sprite = new Sprite(gestorTextura.pelotaJugador);
        this.forma = new CircleShape();
        this.forma.setRadius(0.5f); // (TODO ver el radio de la pelota);

        //Definición de propiedades físicas
        this.formaFixtureDef = new FixtureDef();
        this.formaFixtureDef.shape = forma;
        this.formaFixtureDef.density = 0.5f;
        this.formaFixtureDef.friction = 0.4f;
        this.formaFixtureDef.restitution = 0.6f;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody; // el jugador es un cuerpo dinamico (interactua fisicamente con el mundo)

        this.mundo = mundo;

        //Ver si es necesario en el jugador, supongo que si.
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
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
}
