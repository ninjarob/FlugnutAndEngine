package com.pkp.flugnut.FlugnutDimensions.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/28/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThrottleInd extends AbstractGameObjectImpl{
    protected Sprite throttleIndSprite;
    protected HUD hud;

    public ThrottleInd(GLGame game, HUD hud, TextureInfoHolder textureInfoHolder) {
        super(game, null, textureInfoHolder);
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
