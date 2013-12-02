package com.pkp.flugnut.FlugnutDimensions.gameObject;

import com.badlogic.gdx.physics.box2d.Body;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidInfo;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/28/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Asteroid extends AbstractGameObjectImpl{
    protected ITiledTextureRegion asteroidTR;
    protected AnimatedSprite asteroidSprite;
    protected AsteroidInfo asteroidInfo;

    public Asteroid(GLGame game, TextureInfoHolder tih, AsteroidInfo asteroidInfo) {
        super(game, tih);
        touchable = true;
        this.asteroidInfo = asteroidInfo;
    }

    @Override
    public Sprite getSprite() {
        return asteroidSprite;
    }

    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        asteroidSprite = new AnimatedSprite(sp.x, sp.y, asteroidTR.getWidth(0)/2, asteroidTR.getHeight(0)/2, asteroidTR, vertexBufferObjectManager);
    }

    @Override
    public abstract void initForScene(PhysicsWorld physics);

    public abstract void animate();

    public void onActionDown(float touchX, float touchY, PhysicsWorld physicsWorld) {

    }
}
