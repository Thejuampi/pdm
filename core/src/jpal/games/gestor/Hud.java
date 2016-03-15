package jpal.games.gestor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by juan on 13/03/16.
 */
public class Hud implements Disposable {

    public static final String FORMATO_LABEL_VIDA = "%01d";
    public static final String FORMATO_LABEL_PUNTAJE = "%06d";
    public static final String FORMATO_LABEL_TIEMPO = "%03d";
    public Stage stage;
    private FitViewport viewport;

    private Integer limiteTiempo;
    private float tiempoTranscurrido;
    private boolean tiempoAlcanzado;
    private static Label labelTiempoRestante, labelTiuloTiempoRestante, labelTituloPuntaje;

    private static Label labelPuntaje;
    private Label labelVidas;
    private static Label labelTituloVidas;

    public static int vidas;
    public static int puntaje;

    public Hud(SpriteBatch spriteBatch) {
        limiteTiempo = 250;
        tiempoTranscurrido = 0;
        puntaje = 0;
        vidas = 3;

        viewport = new FitViewport((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        FreeTypeFontGenerator generadoDeFuentes = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parametrosDeFuente = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parametrosDeFuente.size = 32;
        parametrosDeFuente.shadowColor = Color.LIGHT_GRAY;
        parametrosDeFuente.color = Color.BLACK;

        BitmapFont font = generadoDeFuentes.generateFont(parametrosDeFuente);
        generadoDeFuentes.dispose();

        labelTiempoRestante = new Label(String.format("%03d", limiteTiempo), new Label.LabelStyle(font, Color.WHITE));
        labelPuntaje = new Label(String.format("%06d", puntaje), new Label.LabelStyle(font, Color.WHITE));
        labelVidas = new Label(String.format("%01d", vidas), new Label.LabelStyle(font, Color.WHITE));

        labelTiuloTiempoRestante = new Label("Tiempo Restante", new Label.LabelStyle(font, Color.BLACK));
        labelTituloPuntaje = new Label("Puntos", new Label.LabelStyle(font, Color.BLACK));
        labelTituloVidas = new Label("Vidas", new Label.LabelStyle(font, Color.BLACK));

        Table tabla = new Table();
        tabla.top();
        tabla.setFillParent(true);

        tabla.add(labelTituloPuntaje).expandX().padTop(10);
        tabla.add(labelTiuloTiempoRestante).expandX().padTop(10);
        tabla.add(labelTituloVidas).expandX().padTop(10);
        tabla.row();
        tabla.add(labelPuntaje).expandX();
        tabla.add(labelTiempoRestante).expandX();
        tabla.add(labelVidas).expandX();

        stage.addActor(tabla);
    }

    public void actualizar(float delta) {
        tiempoTranscurrido += delta;
        if (tiempoTranscurrido >= 1) {
            if (limiteTiempo > 0) {
                limiteTiempo--;
            } else {
                tiempoAlcanzado = true;
            }
            labelTiempoRestante.setText(String.format(FORMATO_LABEL_TIEMPO, limiteTiempo));
            tiempoTranscurrido = 0;
        }
    }

    public static void agregarPuntaje(int puntaje) {
        Gdx.app.log("agregarPuntaje() ", String.valueOf(puntaje));
        Hud.puntaje += puntaje;
        labelPuntaje.setText(String.format(FORMATO_LABEL_PUNTAJE, Hud.puntaje));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean limiteTiempoAlcanzado() {
        return tiempoAlcanzado;
    }

    public static Label getLabelPuntaje() {
        return labelPuntaje;
    }

    public static Integer getPuntaje() {
        return puntaje;
    }

    public void agregarVida(int i) {
        Gdx.app.log("agregarVida() ", String.valueOf(i));
        Hud.vidas += i;
        labelVidas.setText(String.format(FORMATO_LABEL_VIDA, vidas));
    }
}