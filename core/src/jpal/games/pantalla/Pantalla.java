package jpal.games.pantalla;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import jpal.games.BounceandoGame;
import jpal.games.Jugador;
import jpal.games.gestor.GestorCamara;
import jpal.games.gestor.GestorPantalla;
import jpal.games.gestor.GestorSprite;

/**
 * Created by juan on 06/02/16.
 */
public class Pantalla extends ScreenAdapter {

    protected GestorPantalla gestor;

    protected String nombre;

    protected Pantalla pantallaAnterior;

    protected Pantalla pantallaSiguiente;

    protected BounceandoGame juego;

//    protected GestorSprite gestorSprite;

    protected Stage stage;

    World mundo;

    Jugador jugador;

    Box2DDebugRenderer debugRender;

    private final float x0 = 0.0f;
    private final float y0 = 0.0f;

    private final float x1 = 50.0f;
    private final float y1 = 20.0f;

    public Pantalla(String nombre, Pantalla anterior, Pantalla siguiente, GestorPantalla gestor, World mundo, BounceandoGame juego) {
        this.nombre = nombre;
        this.pantallaAnterior = anterior;
        this.pantallaSiguiente = siguiente;
        this.gestor = gestor;
//        this.mundo = new World(new Vector2(0.0f,-9.8f), true);
        this.mundo = mundo;
//        this.gestorSprite = GestorSprite.get();

        jugador = new Jugador(mundo);

        this.juego = juego;

        debugRender = new Box2DDebugRenderer();

        init();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }



    public void setPantallaSiguiente(Pantalla pantallaSiguiente) {
        this.pantallaSiguiente = pantallaSiguiente;
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        if( stage  != null) {
            mundo.step(1.0f / 60.0f, 6, 2); // TODO (juan) ver esto..
            debugRender.render(mundo, juego.getMatrizProyeccion());
            stage.act();
            stage.draw();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if(stage != null) {
            stage.dispose();
        }
    }

    protected void init(){


    }

}
