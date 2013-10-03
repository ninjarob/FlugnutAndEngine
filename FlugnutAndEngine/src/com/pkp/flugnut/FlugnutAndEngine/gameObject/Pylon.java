package com.pkp.flugnut.FlugnutAndEngine.gameObject;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.pkp.flugnut.FlugnutAndEngine.screen.global.GameScene;
import com.pkp.flugnut.FlugnutAndEngine.utils.GameConstants;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Pylon extends AbstractGameObjectImpl {
    private AnimatedSprite pylonSprite;
    private float startUpTime = 3;
    private float timeFlugnutPresent = 0;

    protected ITiledTextureRegion pylonTextureRegion;

    private boolean flugnutPresent = false;
    private boolean onFire = false;
    private boolean covered = false;
    private boolean litUp = false;

    public Pylon(GameScene scene, int yOrigForAtlas) {
        super(scene, yOrigForAtlas);
    }

    public Pylon(GameScene scene, int yOrigForAtlas, int scaledWidth, int scaledHeight) {
        super(scene, yOrigForAtlas, scaledWidth, scaledHeight);
    }

    @Override
    public void initResources(String filename, BitmapTextureAtlas mBitmapTextureAtlas) {
        //super.initResources(filename, mBitmapTextureAtlas);
        //this.pylonSingle = TextureRegionFactory.extractFromTexture(mBitmapTextureAtlas, 0, 713, 100, 120);
        this.pylonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, scene.game, filename, 0, 713, 4, 5);
    }

    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        pylonSprite = new AnimatedSprite(sp.x, sp.y, scaledWidth, scaledHeight, pylonTextureRegion, vertexBufferObjectManager);
    }

    //Block Buildings start point is in the lower left
    @Override
    public void initForScene(PhysicsWorld physics) {
        Body pylonBody = PhysicsFactory.createBoxBody(physics, sp.x, sp.y, pylonSprite.getWidthScaled(),
                pylonSprite.getHeightScaled(), BodyDef.BodyType.DynamicBody, GameConstants.BUILDING_FIXTURE_DEF);
        MassData md  = new MassData();
        md.mass = 1f;
        md.I = 0;
        pylonBody.setMassData(md);
        pylonBody.setFixedRotation(true);
        pylonBody.setLinearDamping(1);
        pylonSprite.setUserData(pylonBody);
        scene.attachChild(pylonSprite);
        scene.registerTouchArea(pylonSprite);
        long[] durations = new long[12];
        for (int i = 0; i < 12; i++) {
            durations[i] = 100l;
        }
        pylonSprite.animate(durations, 5, 16, true);
        physics.registerPhysicsConnector(new PhysicsConnector(pylonSprite, pylonBody, true, true));
    }

    public Sprite getSprite() {return pylonSprite;}
    public float getStartUpTime() {return startUpTime;}
    public float getTimeFlugnutPresent() {return timeFlugnutPresent;}
    public boolean getFlugnutPresent() {return flugnutPresent;}
    public boolean getOnFire() {return onFire;}
    public boolean getCovered() {return covered;}
    public boolean getLitUp() {return litUp;}

    public void setStartUpTime(float startUpTime) {this.startUpTime = startUpTime;}
    public void setTimeFlugnutPresent(float timeFlugnutPresent) {this.timeFlugnutPresent = timeFlugnutPresent;}
    public void setFlugnutPresent(boolean flugnutPresent) {this.flugnutPresent = flugnutPresent;}
    public void setOnFire(boolean onFire) {this.onFire = onFire;}
    public void setCovered(boolean covered) {this.covered = covered;}
    public void setLitUp(boolean litUp) {this.litUp = litUp;}

}