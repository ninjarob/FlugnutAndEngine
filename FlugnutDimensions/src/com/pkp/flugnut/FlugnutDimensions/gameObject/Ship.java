package com.pkp.flugnut.FlugnutDimensions.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/28/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Ship extends AbstractGameObjectImpl {

    protected ITiledTextureRegion shipTR;
    protected AnimatedSprite shipSprite;
    protected int shipAnimationIndex=0;
    protected float thrustPercent;
    protected boolean changingDir = false;

    public Ship(GLGame game, TextureInfoHolder textureInfoHolder) {
        super(game, textureInfoHolder);
        touchable = true;
    }

    @Override
    public Sprite getSprite() {
        return shipSprite;
    }

    @Override
    public void initResources(BitmapTextureAtlas mBitmapTextureAtlas) {
        this.shipTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, game, texInfo.getPath(), 0, texInfo.getStarty(), 8, 8);
    }

    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        shipSprite = new AnimatedSprite(sp.x, sp.y, shipTR, vertexBufferObjectManager);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        Vector2 pos = new Vector2(sp.x- shipSprite.getWidth()/2, sp.y- shipSprite.getHeight()/2);
        body = PhysicsFactory.createBoxBody(physics, pos.x, pos.y, shipSprite.getWidth(),
                shipSprite.getHeight(), BodyDef.BodyType.DynamicBody, GameConstants.FLUGNUT_FIXTURE_DEF);
        body.setGravityScale(0);
        body.setFixedRotation(true);
        body.setLinearDamping(0.2f);
        shipSprite.setUserData(this);

        scene.attachChild(shipSprite);
        scene.registerTouchArea(shipSprite);
        physics.registerPhysicsConnector(new PhysicsConnector(shipSprite, body, true, true));
    }

    public int getDestIndex(float angle) {
        float radiansPerFrame = 0.09817477f;  //(2*pi / 64)
        int destIndex = (int)(angle/radiansPerFrame);

        return destIndex;
    }

    public float getAngleFromIndex(float index) {
        float radiansPerFrame = 0.09817477f;  //(2*pi / 64)
        float angle = (index * radiansPerFrame);

        return angle;
    }

    public void rotateShip(int destIndex) {
        if (shipSprite.isAnimationRunning()) {
            shipSprite.stopAnimation();
            shipAnimationIndex = shipSprite.getCurrentTileIndex();
        }
        if (destIndex == shipAnimationIndex) return;
        int rotateRightDif = (shipAnimationIndex > destIndex)?shipAnimationIndex - destIndex:((63 - destIndex) + shipAnimationIndex)+2;
        int rotateLeftDif = (shipAnimationIndex > destIndex)?((63 - shipAnimationIndex) + destIndex)+2:destIndex - shipAnimationIndex;
        if (rotateRightDif < rotateLeftDif) {
            long[] durations = new long[rotateRightDif+1];
            int[] frames = new int[rotateRightDif+1];
            int j = 0;
            for (int i = shipAnimationIndex; i != destIndex; i--) {
                durations[j] = 10l;
                frames[j] = i;
                if (i == 0) i = 64;
                j++;
            }
            shipSprite.animate(durations, frames, false);
        }
        else {
            long[] durations = new long[rotateLeftDif+1];
            int[] frames = new int[rotateLeftDif+1];
            int j = 0;
            for (int i = shipAnimationIndex; i != destIndex; i++) {
                durations[j] = 10l;
                frames[j] = i;
                if (i == 63) i = -1;
                j++;
            }
            shipSprite.animate(durations, frames, false);
        }
        shipAnimationIndex = destIndex;
    }

    public float getThrustPercent() {
        return thrustPercent;
    }

    public void setThrustPercent(float thrustPercent) {
        this.thrustPercent = thrustPercent;
    }

    public boolean isChangingDir() {
        return changingDir;
    }

    public void setChangingDir(boolean changingDir) {
        this.changingDir = changingDir;
    }

    public void onActionDown(float touchX, float touchY, PhysicsWorld physicsWorld) {}

    public void onActionMove(float touchX, float touchY, PhysicsWorld physicsWorld) {}
}
