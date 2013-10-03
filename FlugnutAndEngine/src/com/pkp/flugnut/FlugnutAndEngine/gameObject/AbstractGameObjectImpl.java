package com.pkp.flugnut.FlugnutAndEngine.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
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
    protected boolean liftable = false;
    protected int weight = 0;
    protected RevoluteJoint anchor1;
    protected RevoluteJoint anchor2;
    protected Body body;

    public AbstractGameObjectImpl(GameScene scene, int yOrigForAtlas)
    {
        init(scene, yOrigForAtlas);
    }

    public AbstractGameObjectImpl(GameScene scene, int yOrigForAtlas, int scaledWidth, int scaledHeight)
    {
        init(scene, yOrigForAtlas);
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;
    }

    public AbstractGameObjectImpl(GameScene scene, int yOrigForAtlas, int yOrigForAtlasTouch)
    {
        init(scene, yOrigForAtlas);
        this.yOrigForAtlasTouch = yOrigForAtlasTouch;
    }

    private void init(GameScene scene, int yOrigForAtlas) {
        this.scene = scene;
        this.sp = new Vector2(0,0);
        this.yOrigForAtlas = yOrigForAtlas;
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

    public boolean getLiftable() {
        return liftable;
    }

    public void setLiftable(boolean liftable) {
        this.liftable = liftable;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public RevoluteJoint getAncor1() {
        return anchor1;
    }

    public void setAnchor1(RevoluteJoint j) {
        anchor1 = j;
    }

    public RevoluteJoint getAncor2() {
        return anchor2;
    }

    public void setAnchor2(RevoluteJoint j) {
        anchor2 = j;
    }

    public Body getBody() {
        return body;
    }
}
