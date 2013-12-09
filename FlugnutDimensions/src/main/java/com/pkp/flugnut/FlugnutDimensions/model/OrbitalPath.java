package com.pkp.flugnut.FlugnutDimensions.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import org.andengine.extension.physics.box2d.PhysicsFactory;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 12/7/13
 * Time: 9:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrbitalPath implements PathBehavior{

    private Vector2 gravCenter;
    private Body centerGravBody;  //used to move the asteroid
    private float speed;
    private boolean dir;
    private RevoluteJoint revoluteJoint;
    private GameScene scene;

    public OrbitalPath(Vector2 gravCenter, float speed, boolean dir) {
        this.gravCenter = gravCenter;
        this.speed = speed;
        this.dir = dir;
    }

    public OrbitalPath(Vector2 gravCenter, GameScene scene) {
        this.gravCenter = gravCenter;
        this.scene = scene;
        if (scene != null) {
            centerGravBody = PhysicsFactory.createBoxBody(scene.getPhysicsWorld(), gravCenter.x, gravCenter.y, 1,
                    1, BodyDef.BodyType.StaticBody, GameConstants.ASTEROID_FIXTURE_DEF);
        }
    }

    public RevoluteJoint getRevoluteJoint() {
        return revoluteJoint;
    }

    public void setRevoluteJoint(RevoluteJoint pullJoint) {
        this.revoluteJoint = pullJoint;
    }


    public Body getCenterGravBody() {
        return centerGravBody;
    }

    public void setCenterGravBody(Body centerGravBody) {
        this.centerGravBody = centerGravBody;
    }

    public Vector2 getGravCenter() {
        return gravCenter;
    }

    public void setGravCenter(Vector2 gravCenter) {
        this.gravCenter = gravCenter;
    }

    public void pathSetup(AsteroidInfo asteroidInfo) {
        final RevoluteJointDef revoluteJointDef = getAsteroidRevoluteJointDef(asteroidInfo);
        revoluteJoint = (RevoluteJoint)(scene.getPhysicsWorld().createJoint(revoluteJointDef));
    }

    public void pathUpdate(AsteroidInfo asteroidInfo, Vector2 pos) {
        if (null!=revoluteJoint) {
            float faster = getOrbitalAsteroidFaster(pos.x, pos.y, asteroidInfo.getAsteroid().getBody(), dir);
            revoluteJoint.setMotorSpeed(revoluteJoint.getMotorSpeed()+faster);
        }
        else {
            if (scene != null && centerGravBody==null) {
                centerGravBody = PhysicsFactory.createBoxBody(scene.getPhysicsWorld(), gravCenter.x, gravCenter.y, 1,
                        1, BodyDef.BodyType.StaticBody, GameConstants.ASTEROID_FIXTURE_DEF);
            }
            final RevoluteJointDef revoluteJointDef = getAsteroidRevoluteJointDef(asteroidInfo);
            revoluteJoint = (RevoluteJoint)(scene.getPhysicsWorld().createJoint(revoluteJointDef));
        }
    }

    public RevoluteJointDef getAsteroidRevoluteJointDef(AsteroidInfo asteroidInfo) {
        final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(centerGravBody, asteroidInfo.getAsteroid().getBody(), centerGravBody.getWorldCenter());
        revoluteJointDef.enableMotor = true;
        revoluteJointDef.motorSpeed = asteroidInfo.getVelMag();
        revoluteJointDef.maxMotorTorque = 10;
        return revoluteJointDef;
    }

    public void clean() {
        if (revoluteJoint != null) {
            scene.getPhysicsWorld().destroyJoint(revoluteJoint);
        }
    }

    protected float getOrbitalAsteroidFaster(float x, float y, Body b, boolean clockwise) {
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
}
