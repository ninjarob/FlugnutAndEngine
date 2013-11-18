package com.pkp.flugnut.FlugnutDimensions.utils;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.GameTextureAtlasManager;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import com.pkp.flugnut.FlugnutDimensions.game.TextureType;
import com.pkp.flugnut.FlugnutDimensions.gameObject.*;
import com.pkp.flugnut.FlugnutDimensions.level.GameSceneInfo;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidArea;
import com.pkp.flugnut.FlugnutDimensions.model.PlayerInfo;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;
import java.util.List;

public class GenerateWorldObjects {

    public GenerateWorldObjects() {

    }

    public GameSceneInfo generateSolSystem(SFSObject dataHolder, GLGame game) {
        //init atlas
        GameTextureAtlasManager gtam = initSpaceGTAM(dataHolder);
        BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), gtam.getWidth()+10, gtam.getHeight()+10, TextureOptions.DEFAULT);

        //init vertexbuffer
        VertexBufferObjectManager vertexBufferObjectManager = game.getVertexBufferObjectManager();

        //get top level params
        Integer systemId = dataHolder.getInt("id");
        Integer bgi = dataHolder.getInt("bgi");
        Vector2 shipPos = new Vector2(dataHolder.getFloat("spx"), dataHolder.getFloat("spy"));
        Integer shipType = dataHolder.getInt("st");
        Integer systemRadius = dataHolder.getInt("rad");
        ISFSArray cbds = dataHolder.getSFSArray("sbds");
        ISFSArray abs = dataHolder.getSFSArray("abs");

        //BACKGROUND
        BitmapTextureAtlas backgroundTexture = new BitmapTextureAtlas(game.getTextureManager(), 512, 512);
        ITextureRegion mapBackground = getMapBackroundTextureRegion(backgroundTexture, game, bgi);

        //SHIP
        Ship ship = getShip(gtam, game, mBitmapTextureAtlas, vertexBufferObjectManager, shipType);

        //SCENE CELESTIAL BODIES
        List<CelestialBody> celestialBodies = getCelestialBodies(gtam, game, cbds, mBitmapTextureAtlas, vertexBufferObjectManager, systemId);

        List<AsteroidArea> asteroidAreas = getAsteroidAreas(abs);

        GameSceneInfo gsi = new GameSceneInfo(asteroidAreas,
                celestialBodies,
                systemRadius,
                systemId,
                shipPos,
                ship,
                backgroundTexture,
                mapBackground,
                vertexBufferObjectManager,
                mBitmapTextureAtlas);
        return gsi;
    }

    private GameTextureAtlasManager initSpaceGTAM(SFSObject dataHolder) {
        GameTextureAtlasManager gtam = new GameTextureAtlasManager();
        gtam.addTexture("Ship/gawain.png", TextureType.GAWAIN, 448, 448);
        String[] sysrecs = dataHolder.getUtfString("sysrec").split(",");
        for (String sysrec:sysrecs) {
            switch(Integer.parseInt(sysrec)) {
                case 1:
                    gtam.addTexture("Background/star/TheSun.png", TextureType.SUN, 700, 700);
                    break;
                case 2:
                    gtam.addTexture("Background/planet/M01.png", TextureType.EARTH, 200, 200);
                    break;
            }
        }
        return gtam;
    }

    private ITextureRegion getMapBackroundTextureRegion(BitmapTextureAtlas backgroundTexture, GLGame game, int backgroundId) {
        switch(backgroundId) {
            case 1:
                TextureRegion mapBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTexture, game, "Background/neb/nebula02.png", 0, 0);
                mapBackground.setTextureSize(320,480);
                return mapBackground;
        }
        return BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTexture, game, "Background/neb/nebula02.png", 0, 0);
    }

    private Ship getShip(GameTextureAtlasManager gtam,
                         GLGame game,
                         BitmapTextureAtlas mBitmapTextureAtlas,
                         VertexBufferObjectManager vertexBufferObjectManager,
                         int shipType) {
        TextureInfoHolder tih;
        switch(shipType) {
            case 1:
                tih = gtam.getTextureInfoHolder(TextureType.GAWAIN);
                break;
            default:
                tih = gtam.getTextureInfoHolder(TextureType.GAWAIN);
        }
        Ship ship = new GawainShip(game, tih);
        ship.initResources(mBitmapTextureAtlas);
        ship.initSprites(vertexBufferObjectManager);
        return ship;
    }

    private List<CelestialBody> getCelestialBodies(GameTextureAtlasManager gtam,
                                                   GLGame game,
                                                   ISFSArray cbds,
                                                   BitmapTextureAtlas mBitmapTextureAtlas,
                                                   VertexBufferObjectManager vertexBufferObjectManager,
                                                   int systemId) {
        List<CelestialBody> celestialBodies = new ArrayList<CelestialBody>();
        TextureInfoHolder tih;
        for (int i = 0; i < cbds.size(); i++) {
            ISFSObject sfsObj = cbds.getSFSObject(i);
            Vector2 loc = new Vector2(sfsObj.getFloat("x"), sfsObj.getFloat("y"));
            Integer bodyType = sfsObj.getInt("bt");
            Integer bodyRadius = sfsObj.getInt("rad");
            switch (bodyType) {
                case 1:
                    tih = new TextureInfoHolder(0, gtam.getStarty(TextureType.SUN), gtam.getPath(TextureType.SUN));
                    Star sun = new Star(game, tih, systemId, loc, 1, bodyRadius);
                    sun.initResources(mBitmapTextureAtlas);
                    sun.initSprites(vertexBufferObjectManager);
                    celestialBodies.add(sun);
                    break;
                case 2:
                    tih = new TextureInfoHolder(0, gtam.getStarty(TextureType.EARTH), gtam.getPath(TextureType.EARTH));
                    Planet earth = new Planet(game, tih, systemId, loc, 2, bodyRadius);
                    earth.initResources(mBitmapTextureAtlas);
                    earth.initSprites(vertexBufferObjectManager);
                    celestialBodies.add(earth);
                    break;
            }
        }
        return celestialBodies;
    }

    private List<AsteroidArea> getAsteroidAreas(ISFSArray abs) {
        List<AsteroidArea> asteroidAreas = new ArrayList<AsteroidArea>();
        for (int i = 0; i < abs.size(); i++) {
//            ISFSObject sfsObj = abs.getSFSObject(i);
//            float x1 = sfsObj.getFloat("x1");
//            float y1 = sfsObj.getFloat("y1");
//            float x2 = sfsObj.getFloat("x2");
//            float y2 = sfsObj.getFloat("y2");
//            int numberOfAsteroids = sfsObj.getInt("num");
//
//            AsteroidArea ab = new AsteroidArea(new Vector2(x1, y1), new Vector2(x2,y2), numberOfAsteroids);
//            asteroidAreas.add(ab);
        }
        return asteroidAreas;
    }
}
