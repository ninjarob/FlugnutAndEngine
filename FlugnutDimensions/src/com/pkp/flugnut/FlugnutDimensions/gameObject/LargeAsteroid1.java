package com.pkp.flugnut.FlugnutDimensions.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/28/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class LargeAsteroid1 extends Asteroid {

    public LargeAsteroid1(GLGame game, TextureInfoHolder tih) {
        super(game, tih);
    }

    @Override
    public void initResources(BitmapTextureAtlas mBitmapTextureAtlas) {
        this.asteroidTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, game, texInfo.getPath(), 0, texInfo.getStarty(), 6, 4);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        Vector2 pos = new Vector2(sp.x- asteroidSprite.getWidth()/2, sp.y - (asteroidSprite.getHeight()/2));
        body = PhysicsFactory.createBoxBody(physics, pos.x, pos.y, asteroidSprite.getWidth(),
                asteroidSprite.getHeight(), BodyDef.BodyType.KinematicBody, GameConstants.ASTEROID_FIXTURE_DEF);
        body.setFixedRotation(false);
        body.setAngularVelocity(1f);
        asteroidSprite.setUserData(this);

        scene.attachChild(asteroidSprite);
        physics.registerPhysicsConnector(new PhysicsConnector(asteroidSprite, body, true, true));
        //animate();
    }

    public void animate() {
        long[] durations = new long[19];
        int[] frames = new int[19];
        for (int i = 0; i < 19; i++) {
            durations[i] = 100l;
            frames[i] = i;
        }
        asteroidSprite.animate(durations, frames, true);
    }

    public void onActionDown(float touchX, float touchY, PhysicsWorld physicsWorld) {

    }
}
