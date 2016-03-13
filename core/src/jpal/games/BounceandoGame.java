package jpal.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Logger;

import jpal.games.gestor.GestorCamara;
import jpal.games.gestor.GestorPantalla;

public class BounceandoGame extends ApplicationAdapter {

    private Logger logger;

    private SpriteBatch batch;

    //Gestion
    private GestorPantalla gestorPantalla;

    private GestorCamara gestorCamara;

    public boolean hayAcelerometro;

    public boolean hayGiroscopio ;

    @Override
	public void create () {
        logger = new Logger("logger", Logger.DEBUG);
        logger.debug("create()");
        batch = new SpriteBatch();
        gestorPantalla = GestorPantalla.get();
        gestorCamara = GestorCamara.get();

        hayAcelerometro = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        hayGiroscopio   = Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass);

        gestorPantalla.setJuego(this);
        gestorPantalla.crearMenuPrincipal();
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
        gestorPantalla.getPantallaActual().render(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void pause() {
        logger.debug("pause()");
    }

    @Override
    public void resume() {
        logger.debug("resume()");
    }

    @Override
    public void resize(int width, int height) {
//        this.gestorCamara

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        gestorCamara.dispose();
        gestorPantalla.dispose();
    }
}
