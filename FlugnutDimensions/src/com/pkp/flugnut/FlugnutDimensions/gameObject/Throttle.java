package com.pkp.flugnut.FlugnutDimensions.gameObject;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/28/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Throttle extends AbstractGameObjectImpl{

    private ITextureRegion throttleTR;
    private Sprite throttleSprite;
    private ThrottleInd throttleInd;
    private Ship ship;
    private HUD hud;

    public Throttle(GLGame game, HUD hud, int yOrigForThrottleAtlas, ThrottleInd throttleInd, Ship ship) {
        super(game, null, yOrigForThrottleAtlas);
        touchable = true;
        this.throttleInd = throttleInd;
        this.ship = ship;
        this.hud = hud;
    }

    @Override
    public Sprite getSprite() {
        return throttleSprite;
    }

    @Override
    public void initResources(String filename, BitmapTextureAtlas mBitmapTextureAtlas) {
        this.throttleTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, filename, 0, yOrigForAtlas);
    }

    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        throttleSprite = new Sprite(sp.x, sp.y, throttleTR.getWidth(), throttleTR.getHeight(), throttleTR, vertexBufferObjectManager);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        throttleSprite.setUserData(this);
        hud.attachChild(throttleSprite);
        hud.registerTouchArea(throttleSprite);
    }

    private void updateThrottleAndThrust(float touchY) {
        float diff = touchY - throttleSprite.getY();
        if (diff < 0 || diff > throttleSprite.getHeight()) return;
        throttleInd.getSprite().setPosition(throttleInd.getSprite().getX(), touchY);
        float thrustPercent = diff/ throttleSprite.getHeight();
        if (thrustPercent > .05) {
            ship.setThrustPercent(1-(diff/throttleSprite.getHeight()));
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
