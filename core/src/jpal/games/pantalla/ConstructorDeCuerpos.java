package jpal.games.pantalla;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Adaptado por juan on 12/03/16.
 * Se agrega soporte para tiles no cuadrados
 * Créditos: http://gamedev.stackexchange.com/questions/66924/how-can-i-convert-a-tilemap-to-a-box2d-world
 */
public class ConstructorDeCuerpos {

    private static float ppt_x; // pixeles por tile en x
    private static float ppt_y; // pixeles por tile en y

    public static Array<Body> construirCuerpos(Map mapaDeTiles, float ppt_x, float ppt_y, World mundo, String nombreLayer) {
        ConstructorDeCuerpos.ppt_x = ppt_x;
        ConstructorDeCuerpos.ppt_y = ppt_y;
        MapObjects objetos = mapaDeTiles.getLayers().get(nombreLayer != null ? nombreLayer : "objetos").getObjects();

        Array<Body> cuerpos = new Array<Body>();

        for (MapObject objeto : objetos) {

            if (objeto instanceof TextureMapObject) {
                continue;
            }

            Shape forma;

            if (objeto instanceof RectangleMapObject) {
                forma = crearRectangulo((RectangleMapObject) objeto);
            } else if (objeto instanceof PolygonMapObject) {
                forma = crearPoligono((PolygonMapObject) objeto);
            } else if (objeto instanceof PolylineMapObject) {
                forma = crearPolilinea((PolylineMapObject) objeto);
            } else if (objeto instanceof CircleMapObject) {
                forma = crearCirculo((CircleMapObject) objeto);
            } else {
                continue;
            }

            BodyDef definicionDeCuerpo = new BodyDef();
            definicionDeCuerpo.type = BodyDef.BodyType.StaticBody;
            Body cuerpo = mundo.createBody(definicionDeCuerpo);
            cuerpo.createFixture(forma, 1);

            cuerpos.add(cuerpo);
            forma.dispose();
        }
        return cuerpos;
    }

    private static PolygonShape crearRectangulo(RectangleMapObject objetoRectangulo) {
        Rectangle rectangulo = objetoRectangulo.getRectangle();
        PolygonShape poligono = new PolygonShape();
        Vector2 tamanio = new Vector2((rectangulo.x + rectangulo.width * 0.5f) / ppt_x,
                (rectangulo.y + rectangulo.height * 0.5f) / ppt_y);
        poligono.setAsBox(rectangulo.width * 0.5f / ppt_x,
                rectangulo.height * 0.5f / ppt_y,
                tamanio,
                0.0f);
        return poligono;
    }

    /**
     * @param circleObject
     * @return
     */
    private static CircleShape crearCirculo(CircleMapObject circleObject) {
        Circle circulo = circleObject.getCircle();
        CircleShape forma = new CircleShape();
        forma.setRadius(circulo.radius / ppt_x); // TODO (juan) ver como mejorar esto
        forma.setPosition(new Vector2(circulo.x / ppt_x, circulo.y / ppt_y));
        return forma;
    }

    private static PolygonShape crearPoligono(PolygonMapObject polygonObject) {
        PolygonShape poligono = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] verticesMundo = new float[vertices.length];

        for (int i = 0; i <= vertices.length / 2; ++i) {
//            System.out.println(vertices[i]);
            //Transformo los verticies, x -> [i]; y -> [i+1]
            verticesMundo[i] = vertices[i] / ppt_x;
            verticesMundo[i+1] = vertices[i+1] / ppt_y;
        }

        poligono.set(verticesMundo);
        return poligono;
    }

    private static ChainShape crearPolilinea(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] verticesMundo = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            verticesMundo[i] = new Vector2();
            verticesMundo[i].x = vertices[i * 2] / ppt_x;
            verticesMundo[i].y = vertices[i * 2 + 1] / ppt_y;
        }

        ChainShape forma = new ChainShape();
        forma.createChain(verticesMundo);
        return forma;
    }
}
