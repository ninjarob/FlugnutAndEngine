package com.pkp.flugnut.FlugnutDimensions.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/28/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Ship extends AbstractGameObjectImpl{

    protected ITiledTextureRegion shipTR;
    protected ITiledTextureRegion shipEngineTR;
    protected AnimatedSprite shipSprite;
    protected AnimatedSprite shipEngineSprite;
    private int yOrigForEngineAtlas;
    protected int shipAnimationIndex=1;

    public Ship(GameScene scene, int yOrigForAtlas, int yOrigForEngineAtlas) {
        super(scene, yOrigForAtlas);
    }

    @Override
    public Sprite getSprite() {
        return shipSprite;
    }

    @Override
    public void initResources(String filename, String filename2, BitmapTextureAtlas mBitmapTextureAtlas) {
        this.shipTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, scene.game, filename, 0, yOrigForAtlas, 8, 8);
        //this.shipEngineTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, scene.game, filename2, 0, yOrigForEngineAtlas, 8, 8);
    }

    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        shipSprite = new AnimatedSprite(sp.x, sp.y, shipTR, vertexBufferObjectManager);
        //shipEngineSprite = new AnimatedSprite(sp.x, sp.y, scaledWidth, scaledHeight, shipEngineTR, vertexBufferObjectManager);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        Vector2 pos = new Vector2(sp.x- shipSprite.getWidth()/2, sp.y- shipSprite.getHeight()/2);
        body = PhysicsFactory.createBoxBody(physics, pos.x, pos.y, shipSprite.getWidth(),
                shipSprite.getHeight(), BodyDef.BodyType.DynamicBody, GameConstants.FLUGNUT_FIXTURE_DEF);
        body.setGravityScale(0);
        body.setFixedRotation(true);
        body.setLinearDamping(1);
        shipSprite.setUserData(this);

        scene.attachChild(shipSprite);
        physics.registerPhysicsConnector(new PhysicsConnector(shipSprite, body, true, true));
    }

    public int getDestIndex(float angle) {
        float radiansPerFrame = 0.09817477f;  //(2*pi / 64)
        return Math.round(angle/radiansPerFrame);
    }

    public void rotateShip(int destIndex) {
        if (shipSprite.isAnimationRunning()) {
            shipSprite.stopAnimation();
            shipAnimationIndex = shipSprite.getCurrentTileIndex();
        }
        if (destIndex == shipAnimationIndex) return;
        int rotateRightDif = (shipAnimationIndex > destIndex)?shipAnimationIndex - destIndex:(64 - destIndex) + shipAnimationIndex;
        int rotateLeftDif = (shipAnimationIndex < destIndex)?(64 - shipAnimationIndex) + destIndex:destIndex - shipAnimationIndex;
        if (rotateRightDif < rotateLeftDif) {
            long[] durations = new long[rotateRightDif];
            int[] frames = new int[rotateRightDif];
            int j = 0;
            for (int i = shipAnimationIndex; i != destIndex; i--) {
                durations[j] = 100l;
                j++;
                frames[j] = i;
                if (i == 0) i = 63;
            }
            shipSprite.animate(durations, frames, false);
        }
        else {
            long[] durations = new long[rotateLeftDif];
            int[] frames = new int[rotateLeftDif];
            int j = 0;
            for (int i = shipAnimationIndex; i != destIndex; i++) {
                durations[j] = 100l;
                j++;
                frames[j] = i;
                if (i == 63) i = 0;
            }
            shipSprite.animate(durations, frames, false);
        }
        shipAnimationIndex = destIndex;
    }
}
