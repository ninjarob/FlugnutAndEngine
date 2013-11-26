package com.pkp.flugnut.FlugnutDimensions.utils;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.GameTextureAtlasManager;
import com.pkp.flugnut.FlugnutDimensions.game.ImageResourceCategory;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import com.pkp.flugnut.FlugnutDimensions.game.TextureType;
import com.pkp.flugnut.FlugnutDimensions.gameObject.*;
import com.pkp.flugnut.FlugnutDimensions.level.GameSceneInfo;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidInfo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateWorldObjects {
    private Map<ImageResourceCategory, GameTextureAtlasManager> gtamMap;
    private Map<ImageResourceCategory, BitmapTextureAtlas> atlasMap;

    private GLGame game;
    public GenerateWorldObjects(GLGame game) {
        this.game = game;
        gtamMap = new HashMap<ImageResourceCategory, GameTextureAtlasManager>();
        atlasMap = new HashMap<ImageResourceCategory, BitmapTextureAtlas>();


        GameTextureAtlasManager hudGtam = new GameTextureAtlasManager();
        hudGtam.addTexture(new TextureInfoHolder(TextureType.BUTTONS, 128, 192, "buttons.png"));
        hudGtam.addTexture(new TextureInfoHolder(TextureType.THROTTLE, 30, 220, "throttle.png"));
        hudGtam.addTexture(new TextureInfoHolder(TextureType.THROTTLE_IND, 20, 4, "throttle_ind.png"));
        hudGtam.addTexture(new TextureInfoHolder(TextureType.THROTTLE_BUTTON, 128, 70, "throttleButtons.png"));
        gtamMap.put(ImageResourceCategory.HUD, hudGtam);

        GameTextureAtlasManager gawainGtam = new GameTextureAtlasManager();
        gawainGtam.addTexture(new TextureInfoHolder(TextureType.GAWAIN, 448, 448, "Ship/gawain.png"));
        gawainGtam.addTexture(new TextureInfoHolder(TextureType.GAWAIN_ENGINE, 448, 448, "Ship/gawain_engine.png"));
        gtamMap.put(ImageResourceCategory.GAWAIN, gawainGtam);

        GameTextureAtlasManager solGtam = new GameTextureAtlasManager();
        solGtam.addTexture(new TextureInfoHolder(TextureType.SUN, 960, 960, "Background/star/TheSun.png"));
        gtamMap.put(ImageResourceCategory.SOL, solGtam);

        GameTextureAtlasManager solPlanetsGtam = new GameTextureAtlasManager();
        solPlanetsGtam.addTexture(new TextureInfoHolder(TextureType.MERCURY, 100, 100, "Background/planet/Mercury.png"));
        solPlanetsGtam.addTexture(new TextureInfoHolder(TextureType.VENUS, 220, 220, "Background/planet/Venus.png"));
        solPlanetsGtam.addTexture(new TextureInfoHolder(TextureType.EARTH, 200, 200, "Background/planet/Earth.png"));
        solPlanetsGtam.addTexture(new TextureInfoHolder(TextureType.MARS, 190, 190, "Background/planet/Mars.png"));
        solPlanetsGtam.addTexture(new TextureInfoHolder(TextureType.JUPITER, 400, 400, "Background/planet/Jupiter.png"));
        solPlanetsGtam.addTexture(new TextureInfoHolder(TextureType.SATURN, 340, 231, "Background/planet/Saturn.png"));
        solPlanetsGtam.addTexture(new TextureInfoHolder(TextureType.URANUS, 250, 250, "Background/planet/Uranus.png"));
        solPlanetsGtam.addTexture(new TextureInfoHolder(TextureType.NEPTUNE, 250, 250, "Background/planet/Neptune.png"));
        solPlanetsGtam.addTexture(new TextureInfoHolder(TextureType.PLUTO, 80, 80, "Background/planet/Pluto.png"));
        solPlanetsGtam.addTexture(new TextureInfoHolder(TextureType.EROS, 70, 70, "Background/planet/Eros.png"));
        gtamMap.put(ImageResourceCategory.SOL_PLANETS, solPlanetsGtam);

        GameTextureAtlasManager miscCelestialGtam = new GameTextureAtlasManager();
        miscCelestialGtam.addTexture(new TextureInfoHolder(TextureType.LARGE_ASTEROID1, 165, 165, "Background/LargeAsteroid/LargeAsteroid1.png"));
        gtamMap.put(ImageResourceCategory.MISC_CELESTIAL, miscCelestialGtam);

        GameTextureAtlasManager animatedAsteroid1Gtam = new GameTextureAtlasManager();
        animatedAsteroid1Gtam.addTexture(new TextureInfoHolder(TextureType.ASTEROID1, 1512, 420, "Anim/asteroid.png"));
        gtamMap.put(ImageResourceCategory.ANIMATED_ASTEROID1, animatedAsteroid1Gtam);

        GameTextureAtlasManager nonAnimatedAsteroidsGtam = new GameTextureAtlasManager();
        gtamMap.put(ImageResourceCategory.NON_ANIMATED_ASTEROIDS, nonAnimatedAsteroidsGtam);

        atlasMap.put(ImageResourceCategory.HUD, new BitmapTextureAtlas(game.getTextureManager(),hudGtam.getWidth()+50, hudGtam.getHeight()+50, TextureOptions.DEFAULT));
        atlasMap.put(ImageResourceCategory.GAWAIN, new BitmapTextureAtlas(game.getTextureManager(),gawainGtam.getWidth()+50, gawainGtam.getHeight()+50, TextureOptions.DEFAULT));
        atlasMap.put(ImageResourceCategory.SOL, new BitmapTextureAtlas(game.getTextureManager(),solGtam.getWidth()+50, solGtam.getHeight()+50, TextureOptions.DEFAULT));
        atlasMap.put(ImageResourceCategory.SOL_PLANETS, new BitmapTextureAtlas(game.getTextureManager(),solPlanetsGtam.getWidth()+50, solPlanetsGtam.getHeight()+50, TextureOptions.DEFAULT));
        atlasMap.put(ImageResourceCategory.ANIMATED_ASTEROID1, new BitmapTextureAtlas(game.getTextureManager(),animatedAsteroid1Gtam.getWidth()+50, animatedAsteroid1Gtam.getHeight()+50, TextureOptions.DEFAULT));
        atlasMap.put(ImageResourceCategory.NON_ANIMATED_ASTEROIDS, new BitmapTextureAtlas(game.getTextureManager(),nonAnimatedAsteroidsGtam.getWidth()+50, nonAnimatedAsteroidsGtam.getHeight()+50, TextureOptions.DEFAULT));
        atlasMap.put(ImageResourceCategory.MISC_CELESTIAL, new BitmapTextureAtlas(game.getTextureManager(),miscCelestialGtam.getWidth()+50, miscCelestialGtam.getHeight()+50, TextureOptions.DEFAULT));
    }

    public GameSceneInfo generateSolSystem(SFSObject dataHolder) {
        //init vertexbuffer
        VertexBufferObjectManager vertexBufferObjectManager = game.getVertexBufferObjectManager();

        //get top level params
        Integer systemId = dataHolder.getInt("id");
        Integer bgi = dataHolder.getInt("bgi");
        Vector2 shipPos = new Vector2(dataHolder.getFloat("spx"), dataHolder.getFloat("spy"));
        Integer shipType = dataHolder.getInt("st");
        Integer systemRadius = dataHolder.getInt("rad");
        ISFSArray cbds = dataHolder.getSFSArray("sbds");
        ISFSArray ast = dataHolder.getSFSArray("ast");

        //BACKGROUND
        BitmapTextureAtlas backgroundTexture = new BitmapTextureAtlas(game.getTextureManager(), 512, 512);
        ITextureRegion mapBackground = getMapBackroundTextureRegion(backgroundTexture, game, bgi);

        //SHIP
        Ship ship = getShip(gtamMap, game, vertexBufferObjectManager, shipType);

        //SCENE CELESTIAL BODIES
        List<CelestialBody> celestialBodies = getCelestialBodies(gtamMap, game, cbds, vertexBufferObjectManager);

        List<AsteroidInfo> asteroids = getAsteroids(gtamMap, game, ast, vertexBufferObjectManager);

        GameSceneInfo gsi = new GameSceneInfo(asteroids,
                celestialBodies,
                systemRadius,
                systemId,
                shipPos,
                ship,
                backgroundTexture,
                mapBackground,
                vertexBufferObjectManager,
                gtamMap,
                atlasMap
                );
        return gsi;
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

    private Ship getShip(Map<ImageResourceCategory, GameTextureAtlasManager> gtamMap,
                         GLGame game,
                         VertexBufferObjectManager vertexBufferObjectManager,
                         int shipType) {
        TextureInfoHolder tih;
        switch(shipType) {
            case 1:
                tih = gtamMap.get(ImageResourceCategory.GAWAIN).getTextureInfoHolder(TextureType.GAWAIN);
                break;
            default:
                tih = gtamMap.get(ImageResourceCategory.GAWAIN).getTextureInfoHolder(TextureType.GAWAIN);
        }
        Ship ship = new GawainShip(game, tih);
        ship.initResources(atlasMap.get(ImageResourceCategory.GAWAIN));
        ship.initSprites(vertexBufferObjectManager);
        return ship;
    }

    private List<CelestialBody> getCelestialBodies(Map<ImageResourceCategory, GameTextureAtlasManager> gtamMap,
                                                   GLGame game,
                                                   ISFSArray cbds,
                                                   VertexBufferObjectManager vertexBufferObjectManager) {
        List<CelestialBody> celestialBodies = new ArrayList<CelestialBody>();
        for (int i = 0; i < cbds.size(); i++) {
            ISFSObject sfsObj = cbds.getSFSObject(i);
            int id = sfsObj.getInt("id");
            Vector2 loc = new Vector2(sfsObj.getFloat("x"), sfsObj.getFloat("y"));
            String[] ct = sfsObj.getUtfString("ct").split(",");
            TextureType cbtype = TextureType.getTextureType(Integer.parseInt(ct[0]), Integer.parseInt(ct[1]));
            switch (cbtype) {
                case SUN:
                    Star sun = new Star(game, gtamMap.get(ImageResourceCategory.SOL).getTextureInfoHolder(cbtype), id, loc);
                    sun.initResources(atlasMap.get(ImageResourceCategory.SOL));
                    sun.initSprites(vertexBufferObjectManager);
                    celestialBodies.add(sun);
                    break;
                case MERCURY:
                case VENUS:
                case EARTH:
                case MARS:
                case JUPITER:
                case SATURN:
                case URANUS:
                case NEPTUNE:
                case PLUTO:
                case EROS:
                    Planet planet = new Planet(game, gtamMap.get(ImageResourceCategory.SOL_PLANETS).getTextureInfoHolder(cbtype), id, loc);
                    planet.initResources(atlasMap.get(ImageResourceCategory.SOL_PLANETS));
                    planet.initSprites(vertexBufferObjectManager);
                    celestialBodies.add(planet);
                    break;
                case LARGE_ASTEROID1:
                    Planet largeAsteroid1 = new Planet(game, gtamMap.get(ImageResourceCategory.MISC_CELESTIAL).getTextureInfoHolder(cbtype), id, loc);
                    largeAsteroid1.initResources(atlasMap.get(ImageResourceCategory.MISC_CELESTIAL));
                    largeAsteroid1.initSprites(vertexBufferObjectManager);
                    celestialBodies.add(largeAsteroid1);
                    break;
            }
        }
        return celestialBodies;
    }

    private List<AsteroidInfo> getAsteroids(Map<ImageResourceCategory, GameTextureAtlasManager> gtamMap,
                                            GLGame game,
                                            ISFSArray ast,
                                            VertexBufferObjectManager vertexBufferObjectManager) {
        List<AsteroidInfo> asteroids = new ArrayList<AsteroidInfo>();
        for (int i = 0; i < ast.size(); i++) {
            ISFSObject sfsObj = ast.getSFSObject(i);
            int id = sfsObj.getInt("id");
            float x = sfsObj.getFloat("x");
            float y = sfsObj.getFloat("y");
            float vx = sfsObj.getFloat("vx");
            float vy = sfsObj.getFloat("vy");
            float gx = sfsObj.getFloat("gx");
            float gy = sfsObj.getFloat("gy");
            int type = sfsObj.getInt("t");
            int hp = sfsObj.getInt("hp");

            AsteroidInfo ab = new AsteroidInfo(id, new Vector2(x, y), new Vector2(vx,vy), new Vector2(gx, gy), hp, type);
            Asteroid asteroid;
            switch (type) {
                case 1:
                    asteroid = new Asteroid1(game,gtamMap.get(ImageResourceCategory.ANIMATED_ASTEROID1).getTextureInfoHolder(TextureType.ASTEROID1));
                    asteroid.initResources(atlasMap.get(ImageResourceCategory.ANIMATED_ASTEROID1));
                    break;
                default:
                    asteroid = new Asteroid1(game,gtamMap.get(ImageResourceCategory.ANIMATED_ASTEROID1).getTextureInfoHolder(TextureType.ASTEROID1));
                    asteroid.initResources(atlasMap.get(ImageResourceCategory.ANIMATED_ASTEROID1));

            }

            asteroid.initSprites(vertexBufferObjectManager);
            asteroid.setStartPosition(new Vector2(x, y));
            asteroid.setSv(new Vector2(vx, vy));
            ab.setAsteroid(asteroid);
            asteroids.add(ab);
        }
        return asteroids;
    }
}
