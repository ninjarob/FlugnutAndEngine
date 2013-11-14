package com.pkp.flugnut.FlugnutDimensions.level;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.gameObject.CelestialBody;
import com.pkp.flugnut.FlugnutDimensions.gameObject.Ship;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidArea;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.List;

/**
 * Contains all information and aspects of a gameSceneInfo that are unique to the gameSceneInfo;
 */
public class GameSceneInfo {
    private List<AsteroidArea> asteroidAreas;
    private List<CelestialBody> celestialBodies;
    private Integer systemRadius;
    private Integer systemId;
    private Vector2 startPos;
    private Ship ship;
    private BitmapTextureAtlas backgroundTexture;
    private ITextureRegion mapBackground;
    private VertexBufferObjectManager vertexBufferObjectManager;
    private BitmapTextureAtlas bitMapTextureAtlas;

    public GameSceneInfo(List<AsteroidArea> asteroidAreas,
                         List<CelestialBody> celestialBodies,
                         Integer systemRadius,
                         Integer systemId,
                         Vector2 startPos,
                         Ship ship,
                         BitmapTextureAtlas backgroundTexture,
                         ITextureRegion mapBackground,
                         VertexBufferObjectManager vertexBufferObjectManager,
                         BitmapTextureAtlas bitMapTextureAtlas) {
        this.asteroidAreas = asteroidAreas;
        this.systemRadius = systemRadius;
        this.systemId = systemId;
        this.startPos = startPos;
        this.celestialBodies = celestialBodies;
        this.ship = ship;
        this.backgroundTexture = backgroundTexture;
        this.mapBackground = mapBackground;
        this.vertexBufferObjectManager = vertexBufferObjectManager;
        this.bitMapTextureAtlas = bitMapTextureAtlas;
    }

    public List<CelestialBody> getCelestialBodies() {
        return celestialBodies;
    }

    public void setCelestialBodies(List<CelestialBody> celestialBodies) {
        this.celestialBodies = celestialBodies;
    }

    public List<AsteroidArea> getAsteroidAreas() {
        return asteroidAreas;
    }

    public void setAsteroidAreas(List<AsteroidArea> asteroidAreas) {
        this.asteroidAreas = asteroidAreas;
    }

    public Integer getSystemRadius() {
        return systemRadius;
    }

    public void setSystemRadius(Integer systemRadius) {
        this.systemRadius = systemRadius;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Vector2 getStartPos() {
        return startPos;
    }

    public void setStartPos(Vector2 startPos) {
        this.startPos = startPos;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public BitmapTextureAtlas getBackgroundTexture() {
        return backgroundTexture;
    }

    public void setBackgroundTexture(BitmapTextureAtlas backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public ITextureRegion getMapBackground() {
        return mapBackground;
    }

    public void setMapBackground(ITextureRegion mapBackground) {
        this.mapBackground = mapBackground;
    }

    public VertexBufferObjectManager getVertexBufferObjectManager() {
        return vertexBufferObjectManager;
    }

    public void setVertexBufferObjectManager(VertexBufferObjectManager vertexBufferObjectManager) {
        this.vertexBufferObjectManager = vertexBufferObjectManager;
    }

    public BitmapTextureAtlas getBitMapTextureAtlas() {
        return bitMapTextureAtlas;
    }

    public void setBitMapTextureAtlas(BitmapTextureAtlas bitMapTextureAtlas) {
        this.bitMapTextureAtlas = bitMapTextureAtlas;
    }
}