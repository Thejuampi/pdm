package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by juan on 06/03/16.
 */
public class BotonesFactory {

    private static final Skin skin = construirSkin();

    private static Skin construirSkin() {
        Skin skin = new Skin();
        String defecto = "default";
        String fondo = "backgroud";

        BitmapFont font = new BitmapFont();
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

        skin.add(defecto, font);

        //Create a texture
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add(fondo, new Texture(pixmap));

        //Creo el boton
        textButtonStyle.up = skin.newDrawable(fondo, Color.GRAY);
        textButtonStyle.down = skin.newDrawable(fondo, Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable(fondo, Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable(fondo, Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont(defecto);
        skin.add(defecto, textButtonStyle);

        return skin;
    }


    public static TextButton crearBoton(String texto, ChangeListener listener){
        TextButton boton = new TextButton(texto, skin);
        boton.addListener(listener);

        return boton;
    }


}
