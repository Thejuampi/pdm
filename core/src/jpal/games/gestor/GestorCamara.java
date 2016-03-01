package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by juan on 06/02/16.
 */
public class GestorCamara implements Disposable {

    private static final float ANCHO_MUNDO = 50.0f;

    private static final float LARGO_MUNDO = 20.0f;

    private float altoCamara;

    private float anchoCamara;

    private float anchoPantalla;

    private float altoPantalla;

    private float relacionAspecto;

    private OrthographicCamera camara;

    private Viewport viewport;

    public GestorCamara(SpriteBatch batch) {

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
    public void dispose() {
        //???
    }
}
