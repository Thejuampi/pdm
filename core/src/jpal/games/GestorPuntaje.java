package jpal.games;

/**
 * Created by juan on 13/03/16.
 */
public class GestorPuntaje {

    private Integer puntaje;

    private Jugador jugador;

    private BounceandoGame juego;

    private static GestorPuntaje INSTANCE;

    private GestorPuntaje() {
    }

    public GestorPuntaje get() {
        if (INSTANCE == null) {
            INSTANCE = new GestorPuntaje();
        }
        return INSTANCE;
    }

    public void setEstado(Integer puntajeInicial, Jugador jugador, BounceandoGame juego) {
        this.puntaje = puntajeInicial;
        this.juego = juego;
        this.jugador = jugador;
    }

    public interface TIPO_PUNTO {

        public static final Integer MONEDA = 1; // suma 1 punto

        public static final Integer MONEDA_3 = 3; // suma 3 punto

    }

    public void agregarPuntaje(Integer puntaje) {

        this.puntaje += puntaje;

        if (puntaje > 10) {

            jugador.agregarVida();

        }
    }

}
