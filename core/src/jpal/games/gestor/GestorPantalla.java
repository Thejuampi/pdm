package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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

    private static GestorPantalla gestor;

    private LinkedList<Pantalla> pantallas;

    public Skin skin;

    BounceandoGame juego;
    private Pantalla pantallaActual = null;

    public Pantalla getPantallaActual() {
        return pantallaActual;
    }

    private GestorPantalla() {
        pantallas = Lists.newLinkedList();
        skin = new Skin();
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
     * @param nombre
     * @param id
     * @param mundo
     * @param stage
     * @return
     */
    public Pantalla crearPantalla(String nombre, Integer id, World mundo, Stage stage) {
        Pantalla nuevaPantalla = new Pantalla(nombre, id, this, mundo, juego);
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

        Pantalla menuPrincipal = new Pantalla("Menu principal",0, this, mundoPrincipal, juego) {
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
        Gdx.app.log("crearPantalla1()","Creando pantalla 1");

        Stage stage = new Stage();
        World mundo = new World(Constantes.gravedad, false );
        Pantalla pantalla = crearPantalla("Pantalla 1",1, mundo, stage);
        pantallaActual = pantalla;
        crearRectangulo(0, 0, 10.0f, 1.0f, mundo, true);
        crearRectangulo(0, 3.75f, 8.0f, 1.0f, mundo, true);
        crearRectangulo(-4.5f, 5.5f, 1.0f, 10.0f, mundo, true);
        crearRectangulo(11.5f, 0.0f, 10.0f, 1.0f, mundo, true);
        crearRectangulo(11.5f, 4.0f, 10.0f, 1.0f, mundo, true);

        // Separador
        crearRectangulo(13.5f + 7.5f, 5.0f, 1.0f, 15.0f, mundo, true);

        //la otra pared de la derecha
        crearRectangulo(22.0f + 7.0f, 5.0f, 1.0f, 15.0f, mundo, true);

        //El lugar al que tengo que llegar para ganar
        crearRectangulo(24.75f, 0.0f, 7.0f, 1.0f, mundo, true ,Constantes.GANAR_ID);

        //El piso que si te caes perdes.
        crearRectangulo(20f, -4f, 50f, 1f, mundo, true, Constantes.PERDER_ID);

        return pantalla;
    }

    public Pantalla crearPantalla2() {

        Gdx.app.log("crearPantalla2()","Creando pantalla 2");

        Stage stage = new Stage();
        World mundo = new World(Constantes.gravedad, false );
        Pantalla pantalla = crearPantalla("Pantalla 1", 2, mundo, stage);
        pantallaActual = pantalla;
        crearRectangulo(0, 0, 10.0f, 1.0f, mundo, true);
        crearRectangulo(0, 3.75f, 8.0f, 1.0f, mundo, true);
        crearRectangulo(-4.5f, 5.5f, 1.0f, 10.0f, mundo, true);
        crearRectangulo(11.5f, 0.0f, 10.0f, 1.0f, mundo, true);
        crearRectangulo(11.5f, 4.0f, 10.0f, 1.0f, mundo, true);

        // Separador
        crearRectangulo(13.5f + 7.5f, 5.0f, 1.0f, 15.0f, mundo, true);

        //Si los tocas, perdes!
        crearRectangulo(15.5f + 7.0f, 10.5f, 3.0f, 1.0f, mundo, true, Constantes.PERDER_ID);
        crearRectangulo(20.0f + 7.0f, 10.5f, 3.0f, 1.0f, mundo, true, Constantes.PERDER_ID);

        //la otra pared de la derecha
        crearRectangulo(22.0f + 7.0f, 5.0f, 1.0f, 15.0f, mundo, true);

        //El lugar al que tengo que llegar para ganar
        crearRectangulo(24.75f, 0.0f, 7.0f, 1.0f, mundo, true, Constantes.GANAR_ID);

        //El piso que si te caes perdes.
        crearRectangulo(20f,-4f,50f,1f,mundo, true, Constantes.PERDER_ID);

        return pantalla;

    }

    public Pantalla crearPantalla3() {
        Gdx.app.log("crearPantalla3()","Creando pantalla 3");

        Stage stage = new Stage();
        World mundo = new World(Constantes.gravedad, false );
        Pantalla pantalla = crearPantalla("Pantalla 1", 3, mundo, stage);
        pantallaActual = pantalla;
        crearRectangulo(0, 0, 10.0f, 1.0f, mundo, true);
        crearRectangulo(0, 3.75f, 8.0f, 1.0f, mundo, true);
        crearRectangulo(-4.5f, 5.5f, 1.0f, 10.0f, mundo, true);
        crearRectangulo(11.5f, 0.0f, 10.0f, 1.0f, mundo, true);
//        crearRectangulo(11.5f, 4.0f, 10.0f, 1.0f, mundo, true);

        // Separador
        crearRectangulo(13.5f + 7.5f, 5.0f, 1.0f, 10.0f, mundo, true);

        //Si los tocas, perdes!
        crearRectangulo(15.5f + 6.0f, 6.5f, 3.0f, 1.0f, mundo, true, Constantes.PERDER_ID);
        crearRectangulo(20.0f + 6.0f, 6.5f, 3.0f, 1.0f, mundo, true, Constantes.PERDER_ID);

        //la otra pared de la derecha
        crearRectangulo(22.0f + 7.0f, 5.0f, 1.0f, 10.0f, mundo, true);

        //Si los tocas, perdes!
        crearRectangulo(15.5f + 7.0f, 2.5f, 3.0f, 1.0f, mundo, true, Constantes.PERDER_ID);
        crearRectangulo(20.0f + 7.0f, 2.5f, 3.0f, 1.0f, mundo, true, Constantes.PERDER_ID);


        //El lugar al que tengo que llegar para ganar
        crearRectangulo(24.75f, 0.0f, 7.0f, 1.0f, mundo, true , Constantes.GANAR_ID);

        //El piso que si te caes perdes.
        crearRectangulo(30f,-4f,60f,1f,mundo, true, Constantes.PERDER_ID);

        return pantalla;

    }


    protected Body crearRectangulo(float x, float y, float ancho, float alto, World mundo, boolean esEstatico){
        return crearRectangulo(x,y,ancho, alto, mundo, esEstatico, null);
    }

    /**
     * Crea un rectánguno y lo agrega al mundo
     */
    protected Body crearRectangulo(float x, float y, float ancho, float alto, World mundo, boolean esEstatico, Object userData) {
        Body pBody;
        BodyDef def = new BodyDef();

        if (esEstatico)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x, y);
        def.fixedRotation = true;
        pBody = pantallaActual.getMundo().createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ancho, alto); // TODO (juan) ver si es necesario convertir coordenadas

        if(userData != null) {
            pBody.createFixture(shape, 1.0f).setUserData(userData);
        }else {
            pBody.createFixture(shape, 1.0f);
        }

        shape.dispose();
        return pBody;
    }


    public void cargarSiguienetPantalla() {
        if(this.pantallaActual != null) {
            switch(pantallaActual.getId()) {
                case 0:
                    crearPantalla1();
                    return;
                case 1:
                    crearPantalla2();
                    return;
                case 2:
                    crearPantalla3();
                    return;
            }
        }
    }

    public void dispose() {



    }
}
