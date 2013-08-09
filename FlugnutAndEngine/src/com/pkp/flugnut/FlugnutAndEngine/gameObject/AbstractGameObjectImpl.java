package com.pkp.flugnut.FlugnutAndEngine.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutAndEngine.screen.global.GameScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class AbstractGameObjectImpl implements GameObject {
    protected GameScene scene;
    protected ITextureRegion textureRegion;
    protected ITextureRegion touchAreaTextureRegion;
    protected Vector2 sp;
    protected int yOrigForAtlas;
    protected int yOrigForAtlasTouch;
    protected int scaledWidth;
    protected int scaledHeight;

    public AbstractGameObjectImpl(GameScene scene, int yOrigForAtlas)
    {
        this.scene = scene;
        this.sp = new Vector2(0,0);
        this.yOrigForAtlas = yOrigForAtlas;
    }

    public AbstractGameObjectImpl(GameScene scene, int yOrigForAtlas, int scaledWidth, int scaledHeight)
    {
        this.scene = scene;
        this.sp = new Vector2(0,0);
        this.yOrigForAtlas = yOrigForAtlas;
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;
    }

    public AbstractGameObjectImpl(GameScene scene, int yOrigForAtlas, int yOrigForAtlasTouch)
    {
        this.scene = scene;
        this.sp = new Vector2(0,0);
        this.yOrigForAtlas = yOrigForAtlas;
        this.yOrigForAtlasTouch = yOrigForAtlasTouch;
    }

    @Override
    public void initResources(String filename, BitmapTextureAtlas mBitmapTextureAtlas) {
        this.textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, scene.game, filename, 0, yOrigForAtlas);
    }

    @Override
    public void initResources(String filename, String filename2, BitmapTextureAtlas mBitmapTextureAtlas) {
        this.textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, scene.game, filename, 0, yOrigForAtlas);
        this.touchAreaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, scene.game, filename2, 0, yOrigForAtlasTouch);
    }

    @Override
    public Vector2 getStartPosition() {
        return sp;
    }

    @Override
    public void setStartPosition(Vector2 sp) {
        this.sp = sp;
    }

    @Override
    public abstract Sprite getSprite();

    @Override
    public abstract void initSprites(VertexBufferObjectManager vertexBufferObjectManager);

    @Override
    public abstract void initForScene(PhysicsWorld physics);

    public GameScene getGameScene() {
        return scene;
    }

    public ITextureRegion getTextureRegion() {
        return textureRegion;
    }

    public ITextureRegion getTouchAreaTextureRegion() {
        return touchAreaTextureRegion;
    }

    public int getScaledWidth() {
        return scaledWidth;
    }

    public int getScaledHeight() {
        return scaledHeight;
    }
}
