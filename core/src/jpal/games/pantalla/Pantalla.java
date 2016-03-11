package jpal.games.pantalla;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import jpal.games.BounceandoGame;
import jpal.games.Jugador;
import jpal.games.gestor.GestorPantalla;

/**
 * Created by juan on 06/02/16.
 */
public class Pantalla extends ScreenAdapter {

    private final Integer id;

    protected GestorPantalla gestor;

    protected String nombre;

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
    private Skin skin = buildSkin();

    public Pantalla(String nombre, Integer id, GestorPantalla gestor, World mundo, BounceandoGame juego) {
        this.nombre = nombre;
        this.gestor = gestor;
        this.mundo = mundo;
        this.id = id;
//        this.gestorSprite = GestorSprite.get();
        jugador = new Jugador(this);
        this.juego = juego;
        debugRender = new Box2DDebugRenderer();
        init();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void render(float delta) {
        Vector2 pos = jugador.getPosicion();

        if (juego.hayGiroscopio) {

            //

        }

        if (juego.hayAcelerometro) {
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
        gestor.cargarSiguienetPantalla();
    }

    public void accionAlPerder() {
        gestor.crearMenuPrincipal();
    }

    public World getMundo() {
        return mundo;
    }

    public Integer getId() {
        return id;
    }
}
