package jpal.games.gestor;

import com.badlogic.gdx.graphics.g2d.Sprite;


public class GestorSprite {

    public Sprite pelota;

    GestorTextura gestorTextura;

    protected static GestorSprite instance;

    public static GestorSprite get() {
        if(instance == null) {
            instance = new GestorSprite();
        }
        return instance;
    }

    public GestorSprite(){
        gestorTextura = GestorTextura.get();
        pelota = new Sprite(gestorTextura.pelotaJugador);
    }

}
