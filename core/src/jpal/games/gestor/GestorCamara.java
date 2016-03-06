package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by juan on 06/02/16.
 */
public class GestorCamara implements Screen {

    private static final float ANCHO_MUNDO = 50.0f;

    private static final float LARGO_MUNDO = 20.0f;

    private final Logger logger;

    private float altoCamara;

    private float anchoCamara;

    private float anchoPantalla;

    private float altoPantalla;

    private float relacionAspecto;

    private OrthographicCamera camara;

    private Viewport viewport;

    public GestorCamara(SpriteBatch batch) {
        this.logger = new Logger("GestorCamara");

        altoPantalla = (float) Gdx.graphics.getHeight();
        anchoPantalla = (float)Gdx.graphics.getWidth();

        relacionAspecto = anchoPantalla/altoPantalla;
        altoCamara = 10.0f;
        anchoCamara = anchoCamara *relacionAspecto;

        camara = new OrthographicCamera(anchoCamara, anchoCamara);
//        camara = new OrthographicCamera(640,480);
        viewport = new ScreenViewport(camara);

        batch.setProjectionMatrix(camara.combined);
    }

    @Override
    public void show() {
        logger.debug("nada que mostrar");
    }

    @Override
    public void render(float delta) {
//        logger.debug("nada que mostrar");
    }

    @Override
    public void resize(int width, int height) {
        //TODO (juan) ver como manejar esto.
        this.camara.setToOrtho(false, width / 2, height / 2);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //???
    }
}
