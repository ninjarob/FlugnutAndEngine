package com.pkp.flugnut.FlugnutDimensions.gameObject;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/28/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThrottleBarInd extends AbstractGameObjectImpl{
    protected Sprite throttleIndSprite;
    protected HUD hud;

    public ThrottleBarInd(GLGame game, HUD hud, TextureInfoHolder textureInfoHolder) {
        super(game, textureInfoHolder);
        touchable = true;
        this.hud = hud;
    }

    @Override
    public Sprite getSprite() {
        return throttleIndSprite;
    }

    @Override
    public void initResources(BitmapTextureAtlas mBitmapTextureAtlas) {
        this.textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, texInfo.getPath(), 0, texInfo.getStarty());
    }

    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        throttleIndSprite = new Sprite(sp.x, sp.y, textureRegion.getWidth(), textureRegion.getHeight(), textureRegion, vertexBufferObjectManager);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        throttleIndSprite.setUserData(this);
        hud.attachChild(throttleIndSprite);
    }
}
