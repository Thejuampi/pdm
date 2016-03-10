package jpal.games.pantalla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import jpal.games.BounceandoGame;
import jpal.games.GanarExcepcion;
import jpal.games.Jugador;
import jpal.games.gestor.GestorCamara;
import jpal.games.gestor.GestorPantalla;
import jpal.games.gestor.GestorSprite;
import jpal.games.utiles.PerderExcepcion;

/**
 * Created by juan on 06/02/16.
 */
public class Pantalla extends ScreenAdapter {

    private Vector2 posicionParaVictoria;

    protected GestorPantalla gestor;

    protected String nombre;

    protected Pantalla pantallaAnterior;

    protected Pantalla pantallaSiguiente;

    protected BounceandoGame juego;

//    protected GestorSprite gestorSprite;

    private final boolean hayAcelerometro;

    protected Stage stage;

    World mundo;

    Jugador jugador;

    Box2DDebugRenderer debugRender;

    private final float x0 = 0.0f;
    private final float y0 = 0.0f;

    private final float x1 = 50.0f;
    private final float y1 = 20.0f;
    private Skin skin = buildSkin();


    public Pantalla(String nombre, Pantalla anterior, Pantalla siguiente, GestorPantalla gestor, World mundo, BounceandoGame juego) {
        this.nombre = nombre;
        this.pantallaAnterior = anterior;
        this.pantallaSiguiente = siguiente;
        this.gestor = gestor;
//        this.mundo = new World(new Vector2(0.0f,-9.8f), true);
        this.mundo = mundo;
//        this.gestorSprite = GestorSprite.get();
        jugador = new Jugador(this);
        this.juego = juego;
        debugRender = new Box2DDebugRenderer();
        hayAcelerometro = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        init();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPantallaSiguiente(Pantalla pantallaSiguiente) {
        this.pantallaSiguiente = pantallaSiguiente;
    }

    public void setPosicionParaGanar(Vector2 posicionVictoria) {
        this.posicionParaVictoria = posicionVictoria;
    }

    @Override
    public void render(float delta) {
        Vector2 pos = jugador.getPosicion();

        if (hayAcelerometro) {
            Vector3 aceleracion = new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
            float magnitud = aceleracion.len2();
            if (magnitud > 200.0f) {
                jugador.impulsar(magnitud);
            }

            float orientacion = Gdx.input.getAccelerometerY();
            jugador.moverPorOrientacion(orientacion);
        }

        juego.getGestorCamara().setPosicionCamara(pos.x, pos.y);
        debugRender.render(mundo, juego.getMatrizProyeccion());

        mundo.step(1.0f / 60.0f, 6, 2); // TODO (juan) ver esto..

        if (stage != null) {
            stage.act();
            stage.draw();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (stage != null) {
            stage.dispose();
        }
    }

    protected void init() {


    }

    private static Skin buildSkin() {
        Skin skin = new Skin();
        String defecto = "default";
        String fondo = "backgroud";

        BitmapFont font = new BitmapFont();
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);

        skin.add(defecto, font);

        //Create a texture
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add(fondo, new Texture(pixmap));
        return skin;
    }

    public void accionAlGanar() {

        if("Pantalla 1".equals(gestor.getPantallaActual().nombre) ) {
            gestor.crearPantalla2();
        } else if("Pantalla 2".equals(gestor.getPantallaActual().nombre)) {
            gestor.crearPantalla3();
        } else if("Pantalla 3".equals(gestor.getPantallaActual().nombre)) {
            gestor.crearMenuPrincipal(); // Vuelve al principio.
        }

    }

    public void accionAlPerder() {
        gestor.crearMenuPrincipal();
    }

    public Stage getStage() {
        return stage;
    }

    public World getMundo() {
        return mundo;
    }
}
