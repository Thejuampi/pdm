package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Logger;
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

    public Pantalla crearPantalla(String nombre, World mundo, Stage stage) {
        Pantalla pantallaAnterior = null;
        if (pantallas.size() > 0) {
            pantallaAnterior = pantallas.getLast();
        }
        Pantalla nuevaPantalla = new Pantalla(nombre, pantallaAnterior, null, this, mundo, juego);
        if (pantallaAnterior != null) {
            pantallaAnterior.setPantallaSiguiente(nuevaPantalla);
        }

        nuevaPantalla.setStage(stage);
        pantallas.add(nuevaPantalla);
        return nuevaPantalla;
    }

    public Pantalla crearMenuPrincipal() {
        World mundoPrincipal = new World(Constantes.gravedad, false); // no es necesario para el menú

        Stage stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), juego.getBatch());
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

        Pantalla menuPrincipal = new Pantalla("Menu principal", null, null, this, mundoPrincipal, juego) {
            @Override
            public void render(float delta) {
                if (stage != null) {
                    stage.act();
                    stage.draw();
                }
            }
        };

        menuPrincipal.setStage(stage);
        return menuPrincipal;
    }

    public Pantalla crearPantalla1() {
        Stage stage = new Stage();

        Pantalla pantalla = crearPantalla("Pantalla 1", mundo, stage);
        pantalla.setPosicionParaGanar(new Vector2());
        crearRectangulo(0, 0, 10.0f, 1.0f, mundo, true);
        crearRectangulo(0, 3.75f, 8.0f, 1.0f, mundo, true);
        crearRectangulo(-4.5f, 5.5f, 1.0f, 10.0f, mundo, true);
        crearRectangulo(11.5f, 0.0f, 10.0f, 1.0f, mundo, true);
        crearRectangulo(11.5f, 4.0f, 10.0f, 1.0f, mundo, true);

        return pantalla;
    }


    /**
     * Crea un rectánguno y lo agrega al mundo
     */
    protected Body crearRectangulo(float x, float y, float ancho, float alto, World mundo, boolean esEstatico) {
        Body pBody;
        BodyDef def = new BodyDef();

        if (esEstatico)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x, y);
        def.fixedRotation = true;
        pBody = mundo.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ancho / 2, alto / 2); // TODO (juan) ver si es necesario convertir coordenadas


        pBody.createFixture(shape, 1.0f);

//        pBody.setTransform(pBody.getPosition(), (float)Math.toRadians(90.0));
        shape.dispose();
        return pBody;
    }


    public void cargarSiguienetPantalla(Pantalla pantalla) {

        //TODO (juan) cargarSiguienetPantalla

    }
}
