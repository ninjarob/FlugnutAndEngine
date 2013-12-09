package com.pkp.flugnut.FlugnutDimensions.utils;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.ImageResourceCategory;
import com.pkp.flugnut.FlugnutDimensions.game.TextureType;
import com.pkp.flugnut.FlugnutDimensions.gameObject.npc.Asteroid;
import com.pkp.flugnut.FlugnutDimensions.gameObject.npc.Asteroid1;
import com.pkp.flugnut.FlugnutDimensions.level.GameSceneInfo;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidInfo;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import com.smartfoxserver.v2.entities.data.ISFSObject;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 12/3/13
 * Time: 8:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class AsteroidUtilities {

    public static void addAsteroid(GLGame game, Integer id, GameScene scene, ISFSObject positionObj, GameSceneInfo gsi) {
        Vector2 pos = new Vector2(positionObj.getFloat("x"), positionObj.getFloat("y"));
        Integer hp = positionObj.getInt("hp");
        Integer type = positionObj.getInt("t");
        Float velMag = positionObj.getFloat("vm");
        String path = positionObj.getUtfString("path");
        AsteroidInfo asteroidInfo = new AsteroidInfo(id, pos, velMag, hp, type, path);
        Asteroid asteroid;
        switch (type) {
            case 1:
                asteroid = new Asteroid1(game,gsi.getGtamMap().get(ImageResourceCategory.ANIMATED_ASTEROID1).getTextureInfoHolder(TextureType.ASTEROID1), asteroidInfo);
                asteroid.initResources(gsi.getAtlasMap().get(ImageResourceCategory.ANIMATED_ASTEROID1));
                break;
            default:
                asteroid = new Asteroid1(game,gsi.getGtamMap().get(ImageResourceCategory.ANIMATED_ASTEROID1).getTextureInfoHolder(TextureType.ASTEROID1), asteroidInfo);
                asteroid.initResources(gsi.getAtlasMap().get(ImageResourceCategory.ANIMATED_ASTEROID1));
        }
        Utilities.addWorldObject(asteroid, gsi, pos, scene, ImageResourceCategory.ANIMATED_ASTEROID1);

        asteroidInfo.setAsteroid(asteroid);
        gsi.addAsteroidInfo(asteroidInfo);
    }
}
