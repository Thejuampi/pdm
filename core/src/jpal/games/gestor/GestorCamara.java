package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import jpal.games.utiles.CicloDeVida;

/**
 * Created by juan on 06/02/16.
 */
public class GestorCamara implements CicloDeVida {

    private static final float ANCHO_MUNDO = 50.0f;

    private static final float LARGO_MUNDO = 20.0f;

    private float anchoPantalla;

    private float altoPantalla;

    private float relacionAspecto;

    private OrthographicCamera camara;

    private Viewport viewport;

    public GestorCamara() {

        relacionAspecto = (float)Gdx.graphics.getWidth() /(float) Gdx.graphics.getHeight();
        altoPantalla = 10.0f;
        anchoPantalla = altoPantalla*relacionAspecto;

        camara = new OrthographicCamera(altoPantalla, anchoPantalla);
        viewport = new ScreenViewport(camara);
    }

    //Metodos del ciclo de vida

    @Override
    public void create() {
        //Ver si es necesario
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
