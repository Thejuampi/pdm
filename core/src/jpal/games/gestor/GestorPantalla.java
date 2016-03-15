package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    public Pantalla crearPantallaPerder() {

        World mundo = new World(Constantes.gravedad, false); // no es necesario para el menú
        Stage stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), juego.getBatch());

        TextButton botonContinuar = BotonesFactory.crearBoton("Continuar", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                recargarPantallaActual(pantallaActual.getId());
            }
        });
        TextButton botonSalir = BotonesFactory.crearBoton("Salir", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("", "Boton Salir presionado");
                Gdx.app.exit();
            }
        });

        FreeTypeFontGenerator generadoDeFuentes = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parametrosDeFuente = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parametrosDeFuente.size = 56;//(int) 32f*alto/720;
        parametrosDeFuente.shadowColor = Color.LIGHT_GRAY;
        parametrosDeFuente.color = Color.WHITE;

        BitmapFont fuente = generadoDeFuentes.generateFont(parametrosDeFuente);

        Label labelGanar = new Label("¿Quieres Volver a Intentar?", new Label.LabelStyle(fuente, Color.WHITE));

        Table tabla = new Table();
        tabla.top();
        tabla.setFillParent(true);

        tabla.add(labelGanar).expandX().padTop(20f);
        tabla.row();
        tabla.add(botonContinuar).expandX().padTop(20f);
        tabla.row();
        tabla.add(botonSalir).expandX().padTop(20f);
        tabla.row();

        stage.addActor(tabla);

        //Le seteo el mismo id para pdoer recargar la pantalla.
        Pantalla pantalla = new Pantalla("", pantallaActual.getId(), this, mundo, juego) {
            @Override
            public void render(float delta) {
                if (stage != null) {
                    stage.act();
                    stage.draw();
                }
            }
        };

        pantallaActual = pantalla;
        pantalla.setStage(stage);
        return pantalla;
    }

    public Pantalla crearPantallaGanar() {
        World mundo = new World(Constantes.gravedad, false); // no es necesario para el menú
        Stage stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), juego.getBatch());

        TextButton botonContinuar = BotonesFactory.crearBoton("Continuar", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cargarSiguienetPantalla();
            }
        });

        FreeTypeFontGenerator generadoDeFuentes = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parametrosDeFuente = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parametrosDeFuente.size = 56;
        parametrosDeFuente.shadowColor = Color.LIGHT_GRAY;
        parametrosDeFuente.color = Color.BLACK;

        BitmapFont fuente = generadoDeFuentes.generateFont(parametrosDeFuente);

        Label labelGanar = new Label("GANASTE!", new Label.LabelStyle(fuente, Color.BLACK));

        Table tabla = new Table();
        tabla.top();
        tabla.setFillParent(true);

        tabla.add(labelGanar).expandX();
        tabla.row();
        tabla.add(botonContinuar).expandX();
        tabla.row();

        stage.addActor(tabla);

        //le seteo el mismo id para poder cargar la pantalla siguiente
        Pantalla pantalla = new Pantalla("", pantallaActual.getId(), this, mundo, juego) {
            @Override
            public void render(float delta) {
                if (stage != null) {
                    stage.act();
                    stage.draw();
                }
            }
        };

        pantallaActual = pantalla;
        pantalla.setStage(stage);
        return pantalla;
    }

    public Pantalla crearMenuPrincipal() {
        World mundoPrincipal = new World(Constantes.gravedad, false); // no es necesario para el menú
        sonidoBienvenida.play();

        Stage stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), juego.getBatch());
        Gdx.input.setInputProcessor(stage);
        TextButton botonNuevoJuego = BotonesFactory.crearBoton("Nuevo Juego", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pantallaActual.dispose();
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
        pantalla.setFondo(GestorTextura.get().fondoDesierto);

        pantallaActual = pantalla;

        return pantalla;
    }

    public Pantalla crearPantalla2() {
        Gdx.app.log("crearPantalla2()", "Creando pantalla 2");
        Stage stage = new Stage();
        World mundo = new World(Constantes.gravedad, true);
        Pantalla pantalla = crearPantalla("mapa2", 2, mundo, stage);
        pantalla.setFondo(GestorTextura.get().fondoSelva);

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
            pantallaActual.dispose();
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
        if (pantallaActual != null) {
            pantallaActual.dispose();
        }
    }

    public void accionAlGanar() {
        this.cargarSiguienetPantalla();
    }

    public void accionAlPerder() {
        int vidas = Hud.vidas;
        if (vidas == 1) {
            crearMenuPrincipal();
        } else if (vidas > 1) {
            pantallaActual.jugador.quitarUnaVida();
            pantallaActual.hud.agregarVida(-1);
            crearPantallaPerder();
        }
    }

    private void mostrarMensajeGameOver() {
        //TODO (juan)
        pantallaActual.dispose();
        crearMenuPrincipal();
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
