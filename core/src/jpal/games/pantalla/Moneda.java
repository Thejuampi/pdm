package jpal.games.pantalla;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by juan on 13/03/16.
 */
public class Moneda implements TiledMapTile {

    private Body cuerpo;

    private AnimatedTiledMapTile tile;

    private Integer puntaje;

    private boolean visible = true;

    public float x;

    public float y;


    public boolean isVisible() {
        return visible;
    }

    public Moneda(Body cuerpo, AnimatedTiledMapTile tile, Integer puntaje, float x, float y) {
        this.cuerpo = cuerpo;
        this.puntaje = puntaje;
        this.tile = tile;
        this.x = x;
        this.y = y;
    }

    public Body getCuerpo() {
        return cuerpo;
    }

    public AnimatedTiledMapTile getTile() {
        return tile;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    @Override
    public int getId() {
        return tile.getId();
    }

    @Override
    public void setId(int id) {
        tile.setId(id);
    }

    @Override
    public BlendMode getBlendMode() {
        return tile.getBlendMode();
    }

    @Override
    public void setBlendMode(BlendMode blendMode) {
        tile.setBlendMode(blendMode);
    }

    @Override
    public TextureRegion getTextureRegion() {
        return tile.getTextureRegion();
    }

    @Override
    public void setTextureRegion(TextureRegion textureRegion) {
        tile.setTextureRegion(textureRegion);
    }

    @Override
    public float getOffsetX() {
        return tile.getOffsetX();
    }

    @Override
    public void setOffsetX(float offsetX) {
        tile.setOffsetX(offsetX);
    }

    @Override
    public float getOffsetY() {
        return tile.getOffsetY();
    }

    @Override
    public void setOffsetY(float offsetY) {
        tile.setOffsetY(offsetY);
    }

    @Override
    public MapProperties getProperties() {
        return tile.getProperties();
    }
}
