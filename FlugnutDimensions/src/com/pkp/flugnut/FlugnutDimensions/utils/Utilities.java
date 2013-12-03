package com.pkp.flugnut.FlugnutDimensions.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.*;
import com.pkp.flugnut.FlugnutDimensions.game.ImageResourceCategory;
import com.pkp.flugnut.FlugnutDimensions.game.Settings;
import com.pkp.flugnut.FlugnutDimensions.gameObject.GameObject;
import com.pkp.flugnut.FlugnutDimensions.level.GameSceneInfo;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidInfo;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import org.andengine.audio.music.Music;
import org.andengine.audio.sound.Sound;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 6/6/13
 * Time: 8:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class Utilities {

    public static void playMusic(Music mMusic) {
        if (Settings.musicEnabled && mMusic != null && !mMusic.isPlaying()) {
            mMusic.play();
        }
    }

    public static void playSound(Sound s) {
        if (Settings.soundEnabled && s != null) {
            s.play();
        }
    }

    public static MouseJoint createMouseJoint(final IAreaShape sprite, final float pTouchAreaLocalX, final float pTouchAreaLocalY, PhysicsWorld physicsWorld) {
        final Body mGroundBody = physicsWorld.createBody(new BodyDef());
        final Body body = ((GameObject) sprite.getUserData()).getBody();
        final MouseJointDef mouseJointDef = new MouseJointDef();

        final Vector2 localPoint = Vector2Pool.obtain((pTouchAreaLocalX - sprite.getWidth() * 0.5f) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, (pTouchAreaLocalY - sprite.getHeight() * 0.5f) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
        mGroundBody.setTransform(localPoint, 0);

        mouseJointDef.bodyA = mGroundBody;
        mouseJointDef.bodyB = body;
        mouseJointDef.dampingRatio = .99f;
        mouseJointDef.frequencyHz = 30;
        mouseJointDef.maxForce = (30.0f * body.getMass());
        //mouseJointDef.collideConnected = true;

        mouseJointDef.target.set(body.getWorldPoint(localPoint));
        Vector2Pool.recycle(localPoint);

        return (MouseJoint) physicsWorld.createJoint(mouseJointDef);
    }

    public static RevoluteJoint createRevoluteJoint(final IAreaShape sprite1, final IAreaShape sprite2, PhysicsWorld physicsWorld,
                                                        Vector2 pos) {
        final Body body1 = ((GameObject) sprite1.getUserData()).getBody();
        final Body body2 = ((GameObject) sprite2.getUserData()).getBody();
        final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(body1, body2, pos);
        return (RevoluteJoint) physicsWorld.createJoint(revoluteJointDef);
    }

    public static Joint createWeldJoint(final IAreaShape sprite1, final IAreaShape sprite2, PhysicsWorld physicsWorld,
                                                    Vector2 pos) {
        final Body body1 = ((GameObject) sprite1.getUserData()).getBody();
        final Body body2 = ((GameObject) sprite2.getUserData()).getBody();
        final WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.initialize(body1, body2, pos);
        return physicsWorld.createJoint(weldJointDef);
    }

    public static float getAngle(Vector2 vec1, Vector2 vec2) {
        float deltaX = vec2.x - vec1.x;
        float deltaY = -1*(vec2.y - vec1.y);
        float angle = (float) Math.atan2(deltaY, deltaX);

        if(angle < 0){
            angle += 2*Math.PI;
        }
        return angle;
    }

    public static float getAsteroidFaster(float x, float y, AsteroidInfo ai) {
        Body b = ai.getAsteroid().getBody();
        if (ai.getVelMag() > 0) {      //counterclockwise (so -x goes faster and +x goes slower)
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

    public static void addWorldObject(GameObject go, GameSceneInfo gsi, Vector2 pos, GameScene scene, ImageResourceCategory irc) {
        go.initResources(gsi.getAtlasMap().get(irc));
        go.initSprites(gsi.getVertexBufferObjectManager());
        go.setStartPosition(pos);
        go.setScene(scene);
        scene.initNewObjectForScene(go);
        scene.getGameObjects().add(go);
    }
}
