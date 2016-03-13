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
    public final TextureRegion fondoSelva;
    public final TextureRegion fondoDesierto;
    public final TextureRegion fondoComun;

    private static GestorTextura instance;

    public static GestorTextura get(){
        if(instance == null) {
            instance = new GestorTextura();
        }
        return instance;
    }

    private GestorTextura() {

        atlas = new TextureAtlas(files.internal("atlas.pack"));
        pelotaJugador = atlas.findRegion("pelota");
        fondoComun = atlas.findRegion("fondo-comun");
        fondoDesierto = atlas.findRegion("fondo-desierto");
        fondoSelva = atlas.findRegion("fondo-selva");

    }

    @Override
    public void dispose() {
        atlas.dispose();
    }
}
