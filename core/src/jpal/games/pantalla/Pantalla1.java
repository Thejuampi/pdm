package jpal.games.pantalla;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import jpal.games.gestor.GestorPantalla;

/**
 * Created by juan on 24/02/16.
 */
public class Pantalla1 extends Pantalla {

    Sprite suelo;

    Box2DDebugRenderer debugRender;

    public Pantalla1(String nombre, Pantalla anterior, Pantalla siguiente, GestorPantalla gestor, World mundo) {
        super(nombre, anterior, siguiente, gestor, new World(new Vector2(0.0f,-9.81f), true));
        debugRender = new Box2DDebugRenderer();

        suelo = new Sprite(this.gestorSprite.);


    }

}
