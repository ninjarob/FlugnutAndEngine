package com.pkp.flugnut.FlugnutDimensions.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class AbstractGameObjectImpl implements GameObject {
    protected GLGame game;
    protected Scene scene;
    protected ITextureRegion textureRegion;
    protected ITextureRegion touchAreaTextureRegion;
    protected Vector2 sp;
    protected int yOrigForAtlas;
    protected int scaledWidth;
    protected int scaledHeight;
    protected int weight = 0;
    protected RevoluteJoint anchor1;
    protected RevoluteJoint anchor2;
    protected Body body;
    protected boolean touchable;

    public AbstractGameObjectImpl(GLGame game, GameScene scene, int yOrigForAtlas)
    {
        init(game, scene, yOrigForAtlas);
    }

    public AbstractGameObjectImpl(GLGame game, GameScene scene, int yOrigForAtlas, int scaledWidth, int scaledHeight)
    {
        init(game, scene, yOrigForAtlas);
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;
    }

    private void init(GLGame game, GameScene scene, int yOrigForAtlas) {
        this.scene = scene;
        this.sp = new Vector2(0,0);
        this.yOrigForAtlas = yOrigForAtlas;
        this.game = game;
    }

    @Override
    public void initResources(String filename, BitmapTextureAtlas mBitmapTextureAtlas) {
        this.textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, filename, 0, yOrigForAtlas);
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

    @Override
    public void onActionDown(float touchX, float touchY, PhysicsWorld physicsWorld) {}

    @Override
    public void onActionMove(float touchX, float touchY, PhysicsWorld physicsWorld) {}

    @Override
    public void onActionUp(float touchX, float touchY, PhysicsWorld physicsWorld) {}

    public Scene getScene() {
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

    public boolean isTouchable() {
        return touchable;
    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
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