package com.pkp.flugnut.FlugnutDimensions.gameObject.hud;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import com.pkp.flugnut.FlugnutDimensions.gameObject.AbstractGameObjectImpl;
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
public abstract class ThrottleBar extends AbstractGameObjectImpl {
    protected Sprite throttleSprite;
    protected ThrottleBarInd throttleBarInd;
    protected HUD hud;

    public ThrottleBar(GLGame game, HUD hud, TextureInfoHolder textureInfoHolder, ThrottleBarInd throttleBarInd) {
        super(game, textureInfoHolder);
        touchable = true;
        this.throttleBarInd = throttleBarInd;
        this.hud = hud;
    }

    @Override
    public Sprite getSprite() {
        return throttleSprite;
    }

    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        throttleSprite = new Sprite(sp.x, sp.y, textureRegion.getWidth(), textureRegion.getHeight(), textureRegion, vertexBufferObjectManager);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        throttleSprite.setUserData(this);
        hud.attachChild(throttleSprite);
        hud.registerTouchArea(throttleSprite);
    }

    public abstract void onActionDown(float touchX, float touchY, PhysicsWorld physicsWorld);

    public abstract void onActionMove(float touchX, float touchY, PhysicsWorld physicsWorld);
}
