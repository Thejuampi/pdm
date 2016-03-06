package jpal.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Logger;
import com.google.common.collect.Lists;

import java.util.List;

import jpal.games.gestor.GestorCamara;
import jpal.games.gestor.GestorPantalla;
import jpal.games.gestor.GestorTextura;
import jpal.games.pantalla.Pantalla;

public class BounceandoGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private Logger logger;

    //Gestion
    private GestorPantalla gestorPantalla;

    private GestorCamara gestorCamara;

//    private GestorTextura gestorTextura;

    private List<Screen> screens;

    private Screen pantallaActual;

//    private Box2DDebugRenderer b2dr;

	@Override
	public void create () {
        logger = new Logger("logger", Logger.DEBUG);
        logger.debug("create()");
        batch = new SpriteBatch();
        gestorPantalla = GestorPantalla.get();
        gestorCamara = GestorCamara.get();
//        b2dr = new Box2DDebugRenderer();

        screens = Lists.newArrayList();

        gestorPantalla.setJuego(this);
        Pantalla menuPrincipal = gestorPantalla.crearMenuPrincipal();
        screens.add(menuPrincipal);
        pantallaActual = menuPrincipal;

    }

    public GestorCamara getGestorCamara() {
        return gestorCamara;
    }

    public Matrix4 getMatrizProyeccion() {
        return this.gestorCamara.getMatrizProyeccion();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
	public void render () {
//        logger.debug("render()");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();

        doRender();

//		batch.end();
	}

    @Override
    public void pause() {
        logger.debug("pause()");

    }

    @Override
    public void resume() {
        logger.debug("resume()");
    }

    private void doRender() {

        pantallaActual.render(Gdx.graphics.getDeltaTime());

    }

    public void setPantallaActual(Screen pantalla) {
        this.pantallaActual = pantalla;
    }

    @Override
    public void resize(int width, int height) {
//        this.gestorCamara

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
//        gestorTextura.dispose();
        gestorCamara.dispose();

        for(Screen pantalla : screens) pantalla.dispose();
    }
}
