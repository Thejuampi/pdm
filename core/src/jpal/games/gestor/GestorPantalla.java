package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Logger;
import com.google.common.collect.Lists;

//import org.lwjgl.util.Color;

import java.util.LinkedList;

import jpal.games.pantalla.Pantalla;

import static jpal.games.gestor.Constantes.gravedad;

/**
 * Created by juan on 06/02/16.
 */
public class GestorPantalla {

    private Logger logger;

    private static GestorPantalla gestor;

    private LinkedList<Pantalla> pantallas;

    public Skin skin;

    private GestorPantalla(){
        pantallas = Lists.newLinkedList();
        logger = new Logger("GestorPantalla");
        skin = new Skin();
    }

    public static GestorPantalla get(){
        if(gestor == null) {
            gestor = new GestorPantalla();
        }
        return gestor;
    }

    public Pantalla crearPantalla(String nombre, World mundo) {
        Pantalla pantallaAnterior = null;
        if(pantallas.size() > 0) {
            pantallaAnterior = pantallas.getLast();
        }
        Pantalla nuevaPantalla = new Pantalla(nombre, pantallaAnterior, null, this, mundo);
        if(pantallaAnterior != null) {
            pantallaAnterior.setPantallaSiguiente(nuevaPantalla);
        }
        pantallas.add(nuevaPantalla);
        return nuevaPantalla;
    }

    public Pantalla crearMenuPrincipal() {

        String defecto = "default";
        String fondo = "backgroud";

        Stage stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events

        World mundoPrincipal = new World(gravedad, true);
        BitmapFont font = new BitmapFont();
        Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int) Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
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

        TextButton newGameButton = new TextButton("New game", skin); // Use the initialized skin
        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
        stage.addActor(newGameButton);


        Pantalla menuPrincipal = crearPantalla("Menu Principal", mundoPrincipal);

        return menuPrincipal;

    }

}
