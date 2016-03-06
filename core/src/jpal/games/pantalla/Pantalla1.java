package jpal.games.pantalla;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import jpal.games.gestor.GestorPantalla;
import jpal.games.gestor.GestorSprite;

/**
 * Created by juan on 24/02/16.
 */

@Deprecated
public class Pantalla1 extends Pantalla {

    /**
     * No usar!
     */
    @Deprecated
    Sprite suelo;

//    Box2DDebugRenderer debugRender;

    public Pantalla1(String nombre, Pantalla anterior, Pantalla siguiente, GestorPantalla gestor, World mundo) {
        super(nombre, anterior, siguiente, gestor, mundo);
    }


    @Override
    protected void init() {
        super.init();
//        debugRender = new Box2DDebugRenderer();
//        suelo = GestorSprite.get().crearSuelo();
        //TODO (juan) ver como crear pantalla 1.
    }
}
