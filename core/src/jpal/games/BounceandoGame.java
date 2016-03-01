package jpal.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;

import jpal.games.gestor.GestorCamara;
import jpal.games.gestor.GestorPantalla;
import jpal.games.gestor.GestorTextura;

public class BounceandoGame extends ApplicationAdapter {
	private SpriteBatch batch;
    private Logger logger;

    //Gestion
    private GestorPantalla gestorPantalla;

    private GestorCamara gestorCamara;

    private GestorTextura gestorTextura;

	@Override
	public void create () {
        logger = new Logger("logger", Logger.DEBUG);
        logger.debug("create()");
        batch = new SpriteBatch();
        gestorPantalla = GestorPantalla.get();
        gestorPantalla.crearMenuPrincipal();

        gestorCamara = new GestorCamara(batch);
        gestorTextura = GestorTextura.get();
    }



	@Override
	public void render () {
        logger.debug("render()");
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

        doRender();

		batch.end();
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
        logger.debug("doRreate()");

        batch.draw(gestorTextura.pelotaJugador, 0.0f, 0.0f);

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        gestorTextura.dispose();
        gestorCamara.dispose();
    }
}
