package com.pkp.flugnut.FlugnutDimensions.gameObject;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/28/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThrustThrottle extends ThrottleBar{
    private Ship ship;

    public ThrustThrottle(GLGame game, HUD hud, TextureInfoHolder textureInfoHolder, ThrottleBarInd throttleBarInd, Ship ship) {
        super(game, hud, textureInfoHolder, throttleBarInd);
        touchable = true;
        this.ship = ship;
    }

    private void updateThrottleAndThrust(float touchY) {
        float topOfBar = throttleSprite.getY();
        float bottomOfBar = throttleSprite.getY()+throttleSprite.getHeight();
        float diff = touchY - topOfBar;
        if (diff < 0 || diff > throttleSprite.getHeight()) return;
        float indexYPos = touchY;
        if (diff < 6) indexYPos = topOfBar+6;
        if (diff > throttleSprite.getHeight()-14) indexYPos=bottomOfBar-14;
        throttleBarInd.getSprite().setPosition(throttleBarInd.getSprite().getX(), indexYPos);
        diff = indexYPos - topOfBar;
        float thrustPercent = diff/(throttleSprite.getHeight()-20);
        if (1-thrustPercent > .1) {
            ship.setThrustPercent(1-thrustPercent);
        }
        else {
            ship.setThrustPercent(0);
        }
    }

    public void onActionDown(float touchX, float touchY, PhysicsWorld physicsWorld) {
        updateThrottleAndThrust(touchY);
    }

    public void onActionMove(float touchX, float touchY, PhysicsWorld physicsWorld) {
        updateThrottleAndThrust(touchY);
    }
}
