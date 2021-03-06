package com.pkp.flugnut.FlugnutAndEngine.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.*;
import com.pkp.flugnut.FlugnutAndEngine.game.Settings;
import com.pkp.flugnut.FlugnutAndEngine.gameObject.GameObject;
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

}
