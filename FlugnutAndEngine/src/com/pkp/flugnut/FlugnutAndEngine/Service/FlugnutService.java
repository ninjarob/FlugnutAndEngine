package com.pkp.flugnut.FlugnutAndEngine.Service;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.pkp.flugnut.FlugnutAndEngine.model.level.CarriedObject;
import com.pkp.flugnut.FlugnutAndEngine.screen.global.GameScene;
import com.pkp.flugnut.FlugnutAndEngine.utils.GameConstants;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class FlugnutService {
    private ITextureRegion textureRegion;
    private ITextureRegion touchAreaTextureRegion;
    private CarriedObject carriedObject;  //water, bird, other such thing
    private GameScene scene;

	public FlugnutService(GameScene scene) {
        this.scene = scene;
	}

    public void initResources(String filename, BitmapTextureAtlas mBitmapTextureAtlas, int startYPositionForFlugnut, int startYPositionForEmp) {
        this.textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, scene.game, filename, 0, startYPositionForFlugnut);
        this.touchAreaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, scene.game, "emp1.png", 0, startYPositionForEmp);
    }

    public void initForScene(float x, float y, VertexBufferObjectManager vertexBufferObjectManager, PhysicsWorld physics) {
        final Body body;
        Sprite sprite = new Sprite(x, y, textureRegion, vertexBufferObjectManager);
        Sprite emp = new Sprite(-24, -20, touchAreaTextureRegion, vertexBufferObjectManager);

        Vector2 pos = new Vector2(x-sprite.getWidth()/2, y-sprite.getHeight()/2);
        body = PhysicsFactory.createBoxBody(physics, pos.x, pos.y, sprite.getWidth(), sprite.getHeight(), BodyDef.BodyType.DynamicBody, GameConstants.FLUGNUT_FIXTURE_DEF);
        body.setGravityScale(0);
        body.setFixedRotation(true);
        body.setLinearDamping(1);
        sprite.setUserData(body);
        scene.registerTouchArea(sprite);
        scene.attachChild(sprite);
        sprite.attachChild(emp);
        physics.registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true));
    }

    public ITextureRegion getTextureRegion() {
        return textureRegion;
    }

    public CarriedObject getCarriedObject() {
        return carriedObject;
    }

    public void setCarriedObject(CarriedObject carriedObject) {
        this.carriedObject = carriedObject;
    }

    public boolean isCarrying() {
        return carriedObject!=null;
    }
}