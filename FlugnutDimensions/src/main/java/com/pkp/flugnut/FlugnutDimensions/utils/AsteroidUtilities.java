package com.pkp.flugnut.FlugnutDimensions.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.ImageResourceCategory;
import com.pkp.flugnut.FlugnutDimensions.game.TextureType;
import com.pkp.flugnut.FlugnutDimensions.gameObject.npc.Asteroid;
import com.pkp.flugnut.FlugnutDimensions.gameObject.npc.Asteroid1;
import com.pkp.flugnut.FlugnutDimensions.level.GameSceneInfo;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidInfo;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import org.andengine.extension.physics.box2d.PhysicsFactory;

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
        Vector2 gravCenter = new Vector2(positionObj.getFloat("gx"), positionObj.getFloat("gy"));
        Integer type = positionObj.getInt("t");
        Float velMag = positionObj.getFloat("vm");
        Body centerGravBody = PhysicsFactory.createBoxBody(scene.getPhysicsWorld(), gravCenter.x, gravCenter.y, 1,
                1, BodyDef.BodyType.StaticBody, GameConstants.ASTEROID_FIXTURE_DEF);
        AsteroidInfo asteroidInfo = new AsteroidInfo(id, pos, gravCenter, centerGravBody, velMag, hp, type);
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

        String path = positionObj.getUtfString("path");
        asteroidInfo.setPath(path);
        AsteroidUtilities.pathSetup(asteroidInfo, scene, path);
    }

    public static void updateAsteroid(AsteroidInfo asteroidInfo, GameScene scene, ISFSObject positionObj) {
        Vector2 pos = new Vector2(positionObj.getFloat("x"), positionObj.getFloat("y"));
        Integer hp = positionObj.getInt("hp");
        asteroidInfo.setPos(pos);
        asteroidInfo.setHp(hp);
        String path = asteroidInfo.getPath();
        AsteroidUtilities.pathUpdate(asteroidInfo, scene, path, pos, null);
    }

    public static void removeAsteroid(AsteroidInfo ai, GameScene scene, GameSceneInfo gsi) {
        RevoluteJoint curJoint = ai.getRevoluteJoint();
        if (curJoint != null) {
            scene.getPhysicsWorld().destroyJoint(curJoint);
        }
        scene.getGameObjects().remove(ai);
        gsi.removeAsteroidInfo(ai.getId());
    }

    public static RevoluteJointDef getAsteroidRevoluteJointDef(AsteroidInfo asteroidInfo) {
        final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(asteroidInfo.getCenterGravBody(), asteroidInfo.getAsteroid().getBody(), asteroidInfo.getCenterGravBody().getWorldCenter());
        revoluteJointDef.enableMotor = true;
        revoluteJointDef.motorSpeed = asteroidInfo.getVelMag();
        revoluteJointDef.maxMotorTorque = 10;
        return revoluteJointDef;
    }

    public static float getOrbitalAsteroidFaster(float x, float y, Body b, boolean clockwise) {
        if (clockwise) {      //counterclockwise (so -x goes faster and +x goes slower)
            if (y > 0) {               //below
                if (x < b.getPosition().x) {                  //behind
                    return -0.1f;  //-0.1 means go faster in the counterclockwise direction
                }
                else if (x > b.getPosition().x) {             //ahead
                    return 0.1f;   //go slower
                }
            }
            if (y < 0) {               //above
                if (x < b.getPosition().x) {                  //ahead
                    return 0.1f;    //go slower
                }
                else if (x > b.getPosition().x) {             //behind
                    return -0.1f;   //go faster
                }
            }
        }
        else {                         //clockwise   (so +x goes faster and -x goes slower)
            if (y > 0) {               //below
                if (x < b.getPosition().x) {                  //ahead
                    return -0.1f;  //go slower
                }
                else if (x > b.getPosition().x) {             //behind
                    return 0.1f;   //go faster
                }
            }
            if (y < 0) {               //above
                if (x < b.getPosition().x) {                  //behind
                    return 0.1f;    //go faster
                }
                else if (x > b.getPosition().x) {             //ahead
                    return -0.1f;   //go slower
                }
            }
        }
        return 0;
    }

    public static void pathSetup(AsteroidInfo asteroidInfo, GameScene scene, String path) {
        if("ccco".equals(path)) {
            final RevoluteJointDef revoluteJointDef = AsteroidUtilities.getAsteroidRevoluteJointDef(asteroidInfo);

            RevoluteJoint curJoint = (RevoluteJoint)(scene.getPhysicsWorld().createJoint(revoluteJointDef));
            asteroidInfo.setRevoluteJoint(curJoint);
        }
    }

    public static void pathUpdate(AsteroidInfo asteroidInfo, GameScene scene, String path, Vector2 pos, Vector2 vel) {
        if ("cco".equals(path) || "ccco".equals(path)) {   //circular orbits
            RevoluteJoint curJoint = asteroidInfo.getRevoluteJoint();
            if (null!=curJoint) {
                float faster = AsteroidUtilities.getOrbitalAsteroidFaster(pos.x, pos.y, asteroidInfo.getAsteroid().getBody(), "cco".equals(path));
                curJoint.setMotorSpeed(curJoint.getMotorSpeed()+faster);
            } else {
                RevoluteJointDef revoluteJointDef = getAsteroidRevoluteJointDef(asteroidInfo);
                curJoint = (RevoluteJoint)(scene.getPhysicsWorld().createJoint(revoluteJointDef));
                asteroidInfo.setRevoluteJoint(curJoint);
            }
        }
    }
}
