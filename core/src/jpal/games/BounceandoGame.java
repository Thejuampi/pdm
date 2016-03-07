package jpal.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Logger;
import com.google.common.collect.Lists;

import java.util.List;

import jpal.games.gestor.GestorCamara;
import jpal.games.gestor.GestorPantalla;
import jpal.games.pantalla.Pantalla;

public class BounceandoGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private Logger logger;

    //Gestion
    private GestorPantalla gestorPantalla;

    private GestorCamara gestorCamara;

//    private GestorTextura gestorTextura;

    private List<Pantalla> pantallas;

    private Pantalla pantallaActual;

//    private Box2DDebugRenderer b2dr;

	@Override
	public void create () {
        logger = new Logger("logger", Logger.DEBUG);
        logger.debug("create()");
        pantallas = Lists.newArrayList();
        batch = new SpriteBatch();
        gestorPantalla = GestorPantalla.get();
        gestorCamara = GestorCamara.get();

        gestorPantalla.setJuego(this);
        Pantalla menuPrincipal = gestorPantalla.crearMenuPrincipal();
        pantallas.add(menuPrincipal);

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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gestorCamara.actualizarCamara();
        pantallaActual.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {
        logger.debug("pause()");

    }

    @Override
    public void resume() {
        logger.debug("resume()");
    }

    public void setPantallaActual(Pantalla pantalla) {
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

        for(Screen pantalla : pantallas) pantalla.dispose();
    }
}
