package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by juan on 06/02/16.
 */
public class GestorCamara implements Screen {

    private static final float ANCHO_MUNDO = 50.0f;

    private static final float ALTO_MUNDO = 20.0f;

    private static GestorCamara INSTANCE;

    private final Logger logger;

    private float altoCamara;

    private float anchoCamara;

    private float anchoPantalla;

    private float altoPantalla;

    private float relacionAspecto;

    private OrthographicCamera camara;

    private Viewport viewport;

    /**
     * Limites del mundo
     */
    private float x0 = 0.0f;
    private float x1 = ANCHO_MUNDO;
    private float y0 = 0.0f;
    private float y1 = ALTO_MUNDO;

    public static GestorCamara get() {
        if(INSTANCE == null) {
            INSTANCE = new GestorCamara();
        }
        return INSTANCE;
    }


    protected GestorCamara() {
        this.logger = new Logger("GestorCamara");

        altoPantalla = (float) Gdx.graphics.getHeight();
        anchoPantalla = (float)Gdx.graphics.getWidth();

        relacionAspecto = anchoPantalla/altoPantalla;
        altoCamara = 10.0f;
        anchoCamara = anchoCamara *relacionAspecto;

        camara = new OrthographicCamera(anchoCamara, anchoCamara);
//        camara = new OrthographicCamera(640,480);
        viewport = new ScreenViewport(camara);

    }

    public Matrix4 getMatrizProyeccion() {
        return camara.combined;
    }

    /**
     * Utilizar para posicionar la camara en el mundo.
     * Controla que no se salga de los limites
     * @param x
     * @param y
     */
    public void setPosicionCamara(float x, float y) {

        if (x >= x0 && x <= x1) {
//            Gdx.app.log("","Nuevo x:" + String.valueOf(x));
            this.camara.position.x = x;
        }

        if (y >= y0 && y <= y1) {
//            Gdx.app.log("","Nuevo y:" + String.valueOf(y));
            this.camara.position.y = y;
        }
//        this.camara.position.set(x,y,0f);
    }

    public void panallaToMundo(Vector3 pos) {
        camara.unproject(pos);
    }

    public void mundoToPantalla(Vector3 pos) {
        camara.project(pos);
    }

    public Viewport getViewport() {
        return viewport;
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

    public void actualizarCamara() {
        camara.update();
    }
}
