package com.pkp.flugnut.FlugnutDimensions.level;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.game.GameTextureAtlasManager;
import com.pkp.flugnut.FlugnutDimensions.game.ImageResourceCategory;
import com.pkp.flugnut.FlugnutDimensions.gameObject.Asteroid;
import com.pkp.flugnut.FlugnutDimensions.gameObject.CelestialBody;
import com.pkp.flugnut.FlugnutDimensions.gameObject.Ship;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidInfo;
import com.pkp.flugnut.FlugnutDimensions.model.NPCInfo;
import com.pkp.flugnut.FlugnutDimensions.model.PlayerInfo;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains all information and aspects of a gameSceneInfo that are unique to the gameSceneInfo;
 */
public class GameSceneInfo {
    private Map<Integer, PlayerInfo> playerInfos;
    private Map<Integer, AsteroidInfo> asteroidInfos;
    private Map<Integer, NPCInfo> npcInfos;  //a list of npcs we're tracking or viewing
    private List<CelestialBody> celestialBodies;
    private Integer systemRadius;
    private Integer systemId;
    private Vector2 startPos;
    private Ship ship;
    private BitmapTextureAtlas backgroundTexture;
    private ITextureRegion mapBackground;
    private VertexBufferObjectManager vertexBufferObjectManager;
    private Map<ImageResourceCategory, GameTextureAtlasManager> gtamMap;
    private Map<ImageResourceCategory, BitmapTextureAtlas> atlasMap;

    public GameSceneInfo(List<AsteroidInfo> asteroids,
                         List<CelestialBody> celestialBodies,
                         Integer systemRadius,
                         Integer systemId,
                         Vector2 startPos,
                         Ship ship,
                         BitmapTextureAtlas backgroundTexture,
                         ITextureRegion mapBackground,
                         VertexBufferObjectManager vertexBufferObjectManager,
                         Map<ImageResourceCategory, GameTextureAtlasManager> gtamMap,
                         Map<ImageResourceCategory, BitmapTextureAtlas> atlasMap) {
        this.systemRadius = systemRadius;
        this.systemId = systemId;
        this.startPos = startPos;
        this.celestialBodies = celestialBodies;
        this.ship = ship;
        this.backgroundTexture = backgroundTexture;
        this.mapBackground = mapBackground;
        this.vertexBufferObjectManager = vertexBufferObjectManager;
        this.gtamMap = gtamMap;
        this.atlasMap = atlasMap;

        playerInfos = new HashMap<Integer, PlayerInfo>();
        asteroidInfos = new HashMap<Integer, AsteroidInfo>();
        for (AsteroidInfo a : asteroids)
        {
            addAsteroidInfo(a);
        }
        npcInfos= new HashMap<Integer, NPCInfo>();
    }

    public List<CelestialBody> getCelestialBodies() {
        return celestialBodies;
    }

    public void setCelestialBodies(List<CelestialBody> celestialBodies) {
        this.celestialBodies = celestialBodies;
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

    public Map<Integer, PlayerInfo> getPlayerInfos() {
        return playerInfos;
    }

    public void setPlayerInfos(Map<Integer, PlayerInfo> playerInfos) {
        this.playerInfos = playerInfos;
    }

    public void addPlayerInfo(PlayerInfo pi) {
        playerInfos.put(pi.getId(), pi);
    }

    public void removePlayerInfo(Integer pid) {
        playerInfos.remove(pid);
    }

    public Map<Integer, NPCInfo> getNpcInfos() {
        return npcInfos;
    }

    public void setNpcInfos(Map<Integer, NPCInfo> npcInfos) {
        this.npcInfos = npcInfos;
    }

    public NPCInfo getNpcInfo(Integer id) {
        return npcInfos.get(id);
    }

    public void addNpcInfo(NPCInfo npcInfo) {
        npcInfos.put(npcInfo.getId(), npcInfo);
    }

    public void removeNpcInfo(Integer npcInfoId) {
        npcInfos.remove(npcInfoId);
    }

    public Map<Integer, AsteroidInfo> getAsteroidInfos() {
        return asteroidInfos;
    }

    public void setAsteroidInfos(Map<Integer, AsteroidInfo> asteroidInfos) {
        this.asteroidInfos = asteroidInfos;
    }

    public AsteroidInfo getAsteroidInfo(Integer id) {
        return asteroidInfos.get(id);
    }

    public void addAsteroidInfo(AsteroidInfo ai) {
        asteroidInfos.put(ai.getId(), ai);
    }

    public void removeAsteroidInfo(Integer aid) {
        asteroidInfos.remove(aid);
    }

    public Map<ImageResourceCategory, GameTextureAtlasManager> getGtamMap() {
        return gtamMap;
    }

    public void setGtamMap(Map<ImageResourceCategory, GameTextureAtlasManager> gtamMap) {
        this.gtamMap = gtamMap;
    }

    public Map<ImageResourceCategory, BitmapTextureAtlas> getAtlasMap() {
        return atlasMap;
    }

    public void setAtlasMap(Map<ImageResourceCategory, BitmapTextureAtlas> atlasMap) {
        this.atlasMap = atlasMap;
    }
}