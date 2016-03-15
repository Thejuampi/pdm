package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.math.MathUtils.clamp;

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

    OrthographicCamera camaraHud;


    private Viewport viewport;

    /**
     * Limites del mundo
     */
    private float x0 = 0.0f;
    private float x1 = ANCHO_MUNDO;
    private float y0 = 0.0f;
    private float y1 = ALTO_MUNDO;

    public static GestorCamara get() {
        if (INSTANCE == null) {
            INSTANCE = new GestorCamara();
        }
        return INSTANCE;
    }


    protected GestorCamara() {
        this.logger = new Logger("GestorCamara");

        altoPantalla = (float) Gdx.graphics.getHeight();
        anchoPantalla = (float) Gdx.graphics.getWidth();

        relacionAspecto = anchoPantalla / altoPantalla;

        altoCamara = 10.0f;
        anchoCamara = altoCamara * relacionAspecto;

        camara = new OrthographicCamera();
        camara.setToOrtho(false, anchoCamara, altoCamara);

    }

    public Matrix4 getMatrizProyeccion() {
        return camara.combined;
    }

    /**
     * Utilizar para posicionar la camara en el mundo.
     * Controla que no se salga de los limites
     *
     * @param x
     * @param y
     */
    public void setPosicionCamara(float x, float y) {

//        this.camara.position.x = x;
//        this.camara.position.y = y;
        camara.position.x = clamp(x, 8.3f, 50 - 8.3f);
        camara.position.y = clamp(y, 5f, 15f);

//        if (x >= x0 + 8.3f && x <= x1 - 5.0f) {
//            this.camara.position.x = x;
//        } else {
//            if(x >= x0) {
//                this.camara.position.x = x0;// + 8.3f;
//            } else {
//                this.camara.position.x = x1;//-5f;
//            }
//        }
//        if (y >= y0 + 5.0f && y <= y1 - 5.0f) {
//            this.camara.position.y = y;
//        }else if(y >= y0) {
//            this.camara.position.y = y0;
//        } else {
//            this.camara.position.y = y0;
//        }
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

    public OrthographicCamera getCamara() {
        return camara;
    }
}
