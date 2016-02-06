package jpal.games.gestor;

import com.google.common.collect.Lists;

import java.util.LinkedList;

import jpal.games.pantalla.Pantalla;

/**
 * Created by juan on 06/02/16.
 */
public class GestorPantalla {

    private static GestorPantalla gestor;

    private LinkedList<Pantalla> pantallas;

    private GestorPantalla(){
        pantallas = Lists.newLinkedList();
    }

    public GestorPantalla get(){
        if(gestor == null) {
            gestor = new GestorPantalla();
        }
        return gestor;
    }

    public Pantalla crearPantalla(String nombre) {
        Pantalla pantallaAnterior = pantallas.getLast();
        Pantalla nuevaPantalla = new Pantalla(nombre, pantallaAnterior, null, this);
        pantallaAnterior.setPantallaSiguiente(nuevaPantalla);
        pantallas.add(nuevaPantalla);
        return nuevaPantalla;
    }

    public Pantalla crearMenuPrincipal() {

        Pantalla menuPrincipal = crearPantalla("Menu Principal");

        //TODO (juan) ver que meto aca...

        return menuPrincipal;

    }

}
