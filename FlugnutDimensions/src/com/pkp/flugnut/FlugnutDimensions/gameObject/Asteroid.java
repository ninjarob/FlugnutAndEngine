package com.pkp.flugnut.FlugnutDimensions.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/28/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Asteroid extends AbstractGameObjectImpl{

    protected ITiledTextureRegion asteroidTR;
    protected AnimatedSprite asteroidSprite;;

    public Asteroid(GLGame game, GameScene scene, int yOrigForAtlas) {
        super(game, scene, yOrigForAtlas);
        touchable = true;
    }

    @Override
    public Sprite getSprite() {
        return asteroidSprite;
    }

    @Override
    public void initResources(String filename, BitmapTextureAtlas mBitmapTextureAtlas) {
        this.asteroidTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, game, filename, 0, yOrigForAtlas, 21, 7);
    }

    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        asteroidSprite = new AnimatedSprite(sp.x, sp.y, asteroidTR.getWidth(0)/2, asteroidTR.getHeight(0)/2, asteroidTR, vertexBufferObjectManager);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        Vector2 pos = new Vector2(sp.x- asteroidSprite.getWidth()/2, sp.y - (asteroidSprite.getHeight()/2));
        body = PhysicsFactory.createBoxBody(physics, pos.x, pos.y, asteroidSprite.getWidth(),
                asteroidSprite.getHeight(), BodyDef.BodyType.DynamicBody, GameConstants.FLUGNUT_FIXTURE_DEF);
        body.setGravityScale(0);
        body.setFixedRotation(false);
        body.setAngularVelocity(1f);
        body.setLinearVelocity(5f, 10f);
        asteroidSprite.setUserData(this);

        scene.attachChild(asteroidSprite);
        physics.registerPhysicsConnector(new PhysicsConnector(asteroidSprite, body, true, true));

        long[] durations = new long[143];
        int[] frames = new int[143];
        for (int i = 0; i < 143; i++) {
            durations[i] = 10l;
            frames[i] = i;
        }
        asteroidSprite.animate(durations, frames, true);
    }

    public void onActionDown(float touchX, float touchY, PhysicsWorld physicsWorld) {

    }
}
