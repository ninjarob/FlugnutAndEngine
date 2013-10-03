package com.pkp.flugnut.FlugnutAndEngine.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 8/7/13
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GameObject {

    public void initResources(String filename, String filename2, BitmapTextureAtlas mBitmapTextureAtlas);

    public void initResources(String filename, BitmapTextureAtlas mBitmapTextureAtlas);

    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager);

    public void initForScene(PhysicsWorld physics);

    public Vector2 getStartPosition();

    public void setStartPosition(Vector2 sp);

    public Sprite getSprite();

    public Body getBody();
}
