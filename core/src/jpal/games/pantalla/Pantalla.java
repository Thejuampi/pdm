package jpal.games.pantalla;

import com.badlogic.gdx.ScreenAdapter;

import jpal.games.gestor.GestorPantalla;

/**
 * Created by juan on 06/02/16.
 */
public class Pantalla extends ScreenAdapter {

    private GestorPantalla gestor;

    private String nombre;

    private Pantalla pantallaAnterior;

    private Pantalla pantallaSiguiente;

    public Pantalla(String nombre, Pantalla anterior, Pantalla siguiente, GestorPantalla gestor) {
        this.nombre = nombre;
        this.pantallaAnterior = anterior;
        this.pantallaSiguiente = siguiente;
        this.gestor = gestor;
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
