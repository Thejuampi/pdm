package jpal.games.pantalla;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import jpal.games.gestor.GestorPantalla;
import jpal.games.gestor.GestorSprite;

/**
 * Created by juan on 06/02/16.
 */
public class Pantalla extends ScreenAdapter {

    protected GestorPantalla gestor;

    protected String nombre;

    protected Pantalla pantallaAnterior;

    protected Pantalla pantallaSiguiente;

    protected GestorSprite gestorSprite;

    World mundo;

    public Pantalla(String nombre, Pantalla anterior, Pantalla siguiente, GestorPantalla gestor, World mundo) {
        this.nombre = nombre;
        this.pantallaAnterior = anterior;
        this.pantallaSiguiente = siguiente;
        this.gestor = gestor;
//        this.mundo = new World(new Vector2(0.0f,-9.8f), true);
        this.mundo = mundo;
        this.gestorSprite = GestorSprite.get();
    }

    public void setPantallaSiguiente(Pantalla pantallaSiguiente) {
        this.pantallaSiguiente = pantallaSiguiente;
    }

    public Datos getDatos() {
        return new Datos(this.nombre, this.pantallaAnterior, this.pantallaSiguiente);
    }

    public class Datos {

        public final String nombre;
        public final Pantalla pantallaAnterior;
        public final Pantalla pantallaSiguiente;

        public Datos(String nombre, Pantalla pantallaAnterior, Pantalla pantallaSiguiente) {
            this.nombre = nombre;
            this.pantallaAnterior=pantallaAnterior;
            this.pantallaSiguiente=pantallaSiguiente;
        }

    }



}
