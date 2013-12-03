package com.pkp.flugnut.FlugnutDimensions.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import org.andengine.extension.physics.box2d.PhysicsWorld;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/28/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class LargeAsteroid1 extends CelestialBody {

    private int radius;
    public LargeAsteroid1(GLGame game, TextureInfoHolder tih, Integer id, Vector2 location) {
        super(game, tih, id, location);
        this.radius = tih.getHeight();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void onActionDown(float touchX, float touchY, PhysicsWorld physicsWorld) {

    }
}
