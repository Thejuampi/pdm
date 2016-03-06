package jpal.games.gestor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Logger;
import com.google.common.collect.Lists;

//import org.lwjgl.util.Color;

import java.util.LinkedList;

import jpal.games.BounceandoGame;
import jpal.games.pantalla.Pantalla;
import jpal.games.pantalla.Pantalla1;

import static jpal.games.gestor.Constantes.gravedad;

/**
 * Created by juan on 06/02/16.
 */
public class GestorPantalla {

    private Logger logger;

    private static GestorPantalla gestor;

    private LinkedList<Pantalla> pantallas;

    public Skin skin;

    private World mundo;

    BounceandoGame juego;

    private GestorPantalla() {
        pantallas = Lists.newLinkedList();
        logger = new Logger("GestorPantalla");
        skin = new Skin();
        mundo = new World(Constantes.gravedad, true);
    }

    public void setJuego(BounceandoGame juego) {
        this.juego = juego;
    }

    public static GestorPantalla get() {
        if (gestor == null) {
            gestor = new GestorPantalla();
        }
        return gestor;
    }

    public Pantalla crearPantalla(String nombre, World mundo) {
        Pantalla pantallaAnterior = null;
        if (pantallas.size() > 0) {
            pantallaAnterior = pantallas.getLast();
        }
        Pantalla nuevaPantalla = new Pantalla(nombre, pantallaAnterior, null, this, mundo);
        if (pantallaAnterior != null) {
            pantallaAnterior.setPantallaSiguiente(nuevaPantalla);
        }
        pantallas.add(nuevaPantalla);
        return nuevaPantalla;
    }

    public Pantalla crearMenuPrincipal() {

        String defecto = "default";
        String fondo = "backgroud";
        World mundoPrincipal = null; // no es necesario para el menú

        Stage stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        TextButton botonNuevoJuego = BotonesFactory.crearBoton("Nuevo Juego", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Pantalla pantalla = crearPantalla1();
                juego.setPantallaActual(pantalla);
            }
        });

        TextButton botonSalir = BotonesFactory.crearBoton("Salir", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("", "Boton Salir presionado");
                Gdx.app.exit();
            }
        });

        botonNuevoJuego.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, (Gdx.graphics.getHeight() / 2) + 100);
        botonSalir.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, (Gdx.graphics.getHeight() / 2) + 0);
        
        stage.addActor(botonNuevoJuego);
        stage.addActor(botonSalir);

        Pantalla menuPrincipal = crearPantalla("Menu Principal", mundoPrincipal);
        menuPrincipal.setStage(stage);
        return menuPrincipal;

    }

    public Pantalla crearPantalla1() {
        Pantalla pantalla = crearPantalla("Pantalla 1", mundo);




        return pantalla;
    }

}
