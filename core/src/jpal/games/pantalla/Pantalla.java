package jpal.games.pantalla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import jpal.games.BounceandoGame;
import jpal.games.Jugador;
import jpal.games.gestor.GestorPantalla;
import jpal.games.gestor.Hud;

/**
 * Created by juan on 06/02/16.
 */
public class Pantalla extends ScreenAdapter {

    private final Integer id;

    protected GestorPantalla gestor;

    protected String nombre;

    protected BounceandoGame juego;

    protected Stage stage;

    World mundo;

    public Jugador jugador;

    Box2DDebugRenderer debugRender;

    public Array<Body> paraEliminar = new Array<Body>(10);

    public TiledMap getMapaTiled() {
        return mapaTiled;
    }

    TiledMap mapaTiled;

    TiledMapRenderer mapRenderer;

    Music musicaFondo;

    public Hud hud;

    public void detenerMusicaFondo() {
        if (musicaFondo != null && musicaFondo.isPlaying()) {
            musicaFondo.setLooping(false);
            musicaFondo.stop();
        }
    }

    public Pantalla(String nombre, Integer id, GestorPantalla gestor, World mundo, BounceandoGame juego) {
        this.nombre = nombre;
        this.gestor = gestor;
        this.mundo = mundo;
        this.id = id;
        jugador = new Jugador(this);
        this.juego = juego;
        this.hud = new Hud(juego.getBatch());
        if (id > 0) {
            musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica_fondo.mp3"));
            musicaFondo.setLooping(true);
            musicaFondo.play();


            debugRender = new Box2DDebugRenderer();
            mapaTiled = new TmxMapLoader().load(nombre + ".tmx");

            TiledMapTileLayer capaTerreno = (TiledMapTileLayer) mapaTiled.getLayers().get("terreno");

            float ppt_x = capaTerreno.getTileWidth();
            float ppt_y = capaTerreno.getTileHeight();

            ConstructorDeCuerpos.construirCuerpos(mapaTiled, ppt_x, ppt_y, mundo, "objetos");
            ConstructorDeCuerpos.construirColectables(mapaTiled, ppt_x, ppt_y, mundo, "colectables");

            float escalaUnitaria = 1f / ppt_x;
            mapRenderer = new OrthogonalTiledMapRenderer(mapaTiled, escalaUnitaria, juego.getBatch());

            mapRenderer.setView(juego.getGestorCamara().getCamara());

            init();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void render(float delta) {
        Vector2 pos = jugador.getPosicion();
        hud.actualizar(delta);

        for (Iterator<Body> iterator = paraEliminar.iterator(); iterator.hasNext(); ) {
            Body cuerpo = iterator.next();
            mundo.destroyBody(cuerpo);
            iterator.remove();
        }

        if (juego.hayAcelerometro) {
            Vector3 aceleracion = new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
            float magnitud = aceleracion.len2();
            if (magnitud > 200.0f) {
                jugador.impulsar(magnitud);
            }
            if (!juego.hayCompass) {
                float orientacion = Gdx.input.getAccelerometerY();
                jugador.moverPorOrientacion(orientacion);
            } else {
                float orientacion = Gdx.input.getPitch();
                orientacion /= -7.0f;
                jugador.moverPorOrientacion(orientacion);
            }

        }

        mundo.step(1.0f / 60.0f, 6, 2);

        juego.getGestorCamara().setPosicionCamara(pos.x, pos.y);
        debugRender.render(mundo, juego.getMatrizProyeccion());

        mapRenderer.setView(juego.getGestorCamara().getCamara());
        mapRenderer.render();

        juego.getBatch().setProjectionMatrix(juego.getMatrizProyeccion());
        juego.getBatch().begin();
        jugador.dibujar(juego.getBatch());


        juego.getBatch().end();

        juego.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

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
        if (hud != null) {
            hud.dispose();
        }
    }

    protected void init() {


    }

    public void accionAlGanar() {
        gestor.accionAlGanar();
        detenerMusicaFondo();
    }

    public void accionAlPerder() {
        detenerMusicaFondo();
        gestor.accionAlPerder();
    }

    public World getMundo() {
        return mundo;
    }

    public Integer getId() {
        return id;
    }
}
