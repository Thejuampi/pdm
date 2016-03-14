package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.google.common.collect.Lists;

import java.util.LinkedList;

import jpal.games.BounceandoGame;
import jpal.games.pantalla.Pantalla;

//import org.lwjgl.util.Color;

/**
 * Created by juan on 06/02/16.
 */
public class GestorPantalla {

    private static GestorPantalla gestor;

    private LinkedList<Pantalla> pantallas;

    public Skin skin;

    BounceandoGame juego;

    private Pantalla pantallaActual = null;

    private Sound sonidoBienvenida;

    private Sound sonidoIniciarJuegoNuevo;

    public Pantalla getPantallaActual() {
        return pantallaActual;
    }

    private GestorPantalla() {
        pantallas = Lists.newLinkedList();
        skin = new Skin();
        sonidoBienvenida = Gdx.audio.newSound(Gdx.files.internal("sonidos/inicio_juego_bienvenida.wav"));
        sonidoIniciarJuegoNuevo = Gdx.audio.newSound(Gdx.files.internal("sonidos/iniciar_nuevo_juego.wav"));
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

    /**
     * Crea una pantalla y la agrega a la lista de pantallas
     */
    public Pantalla crearPantalla(String nombre, Integer id, World mundo, Stage stage) {
        Pantalla nuevaPantalla = new Pantalla(nombre, id, this, mundo, juego);
        nuevaPantalla.setStage(stage);
        pantallas.add(nuevaPantalla);
        return nuevaPantalla;
    }

    public Pantalla crearMenuPrincipal() {
        World mundoPrincipal = new World(Constantes.gravedad, false); // no es necesario para el menú

        sonidoBienvenida.play();


        Stage stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), juego.getBatch());
        Gdx.input.setInputProcessor(stage);

        TextButton botonNuevoJuego = BotonesFactory.crearBoton("Nuevo Juego", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                crearPantalla1();
            }
        });

        TextButton botonSalir = BotonesFactory.crearBoton("Salir", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("", "Boton Salir presionado");
                Gdx.app.exit();
            }
        });

        int alto = Gdx.graphics.getHeight();
        int ancho = Gdx.graphics.getWidth();

        // la idea es que la pantalla tenga una resolucion virtual de ~16x9
        // cada boton tiene un tamaño de 6x2 con una separación de 3 unidades a lo alto
        // el x en ambos es igual, lo que varia es el y

        float anchoBotones = 6.0f / 16.0f * ancho;
        float altoBotones = 2.0f / 9.0f * alto;

        botonNuevoJuego.setSize(anchoBotones, altoBotones);
        botonNuevoJuego.setPosition((ancho - anchoBotones) / 2.0f, 7.0f / 9.0f * (alto - altoBotones));

        botonSalir.setSize(anchoBotones, altoBotones);
        botonSalir.setPosition((ancho - anchoBotones) / 2.0f, 3.0f / 9.0f * (alto - altoBotones));

        stage.addActor(botonNuevoJuego);
        stage.addActor(botonSalir);

        // Por defecto, pantalla 0 es menu principal
        Pantalla menuPrincipal = new Pantalla("", 0, this, mundoPrincipal, juego) {
            @Override
            public void render(float delta) {
                if (stage != null) {
                    stage.act();
                    stage.draw();
                }
            }
        };

        pantallaActual = menuPrincipal;
        menuPrincipal.setStage(stage);
        return menuPrincipal;
    }

    public Pantalla crearPantalla1() {
        Gdx.app.log("crearPantalla1()", "Creando pantalla 1");

        sonidoIniciarJuegoNuevo.play();

        Stage stage = new Stage();
        World mundo = new World(Constantes.gravedad, true);
        Pantalla pantalla = crearPantalla("mapa1", 1, mundo, stage);
        pantallaActual = pantalla;

        return pantalla;
    }

    public Pantalla crearPantalla2() {

        Gdx.app.log("crearPantalla2()", "Creando pantalla 2");
        Stage stage = new Stage();
        World mundo = new World(Constantes.gravedad, true);
        Pantalla pantalla = crearPantalla("mapa2", 2, mundo, stage);
        pantallaActual = pantalla;

        return pantalla;

    }

    public Pantalla crearPantalla3() {
        Gdx.app.log("crearPantalla3()", "Creando pantalla 3");
        Stage stage = new Stage();
        World mundo = new World(Constantes.gravedad, true);
        Pantalla pantalla = crearPantalla("mapa3", 3, mundo, stage);
        pantallaActual = pantalla;

        return pantalla;
    }

    public void cargarSiguienetPantalla() {
        if (this.pantallaActual != null) {
            switch (pantallaActual.getId()) {
                case 0:
                    crearPantalla1();
                    return;
                case 1:
                    crearPantalla2();
                    return;
                case 2:
                    crearPantalla3();
                    return;
                case 3:
                    crearMenuPrincipal();
            }
        }
    }

    public void dispose() {


    }

    public void accionAlGanar() {
        this.cargarSiguienetPantalla();
    }

    public void accionAlPerder() {
        int vidas = pantallaActual.jugador.getVidas();
        if (vidas == 1) {
            mostrarMensajeGameOver();
        } else if (vidas > 1) {

            pantallaActual.jugador.quitarUnaVida();
            recargarPantallaActual(pantallaActual.getId());
        }
    }

    private void mostrarMensajeGameOver() {
        //TODO (juan)


    }

    private void recargarPantallaActual(Integer id) {
        pantallaActual.dispose();
        switch (id) {
            case 0:
                crearMenuPrincipal();
                return;
            case 1:
                crearPantalla1();
                return;
            case 2:
                crearPantalla2();
                return;
            case 3:
                crearPantalla3();
        }
    }
}
