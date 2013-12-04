package com.pkp.flugnut.FlugnutDimensions.gameObject.npc;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import com.pkp.flugnut.FlugnutDimensions.gameObject.AbstractGameObjectImpl;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/4/13
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class CelestialBody extends AbstractGameObjectImpl {
    protected Sprite sprite;
    private int id;
    private Vector2 location;

    public CelestialBody(GLGame game,
                         TextureInfoHolder tih,
                         int id,
                         Vector2 location) {
        super(game, tih);
        sp = location;
        this.id = id;
        touchable = true;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void initResources(BitmapTextureAtlas mBitmapTextureAtlas) {
        this.textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, texInfo.getPath(), 0, texInfo.getStarty());
    }

    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        sprite = new Sprite(sp.x-(textureRegion.getWidth()/2), sp.y-(textureRegion.getHeight()/2), textureRegion.getWidth(), textureRegion.getHeight(), textureRegion, vertexBufferObjectManager);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        sprite.setUserData(this);
        scene.attachChild(sprite);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector2 getLocation() {
        return location;
    }

    public void setLocation(Vector2 location) {
        this.location = location;
    }

    public ITextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(ITextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
}
