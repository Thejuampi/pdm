package jpal.games.gestor;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import static com.badlogic.gdx.Gdx.files;

/**
 * Created by juan on 07/02/16.
 */
public class GestorTextura implements Disposable{

    private TextureAtlas atlas;

    public final TextureRegion pelotaJugador;

    private static GestorTextura instance;

    public static GestorTextura get(){
        if(instance == null) {
            instance = new GestorTextura();
        }
        return instance;
    }

    private GestorTextura() {

        atlas = new TextureAtlas(files.internal("altas.pack"));

//        bloqueMadera = atlas.findRegion(Regiones.WOOD_BLOCK);
//        bloquePasto = atlas.findRegion(Regiones.GRASS_BLOCK);
        pelotaJugador = atlas.findRegion(Regiones.WHITE_POOL_BALL);

    }

    @Override
    public void dispose() {
        atlas.dispose();
    }
}

interface Regiones {

    public static final String
            GRASS_BLOCK = "Grass Block",
            WOOD_BLOCK  ="Wood Block",
            WHITE_POOL_BALL = "white-pool-ball";


}