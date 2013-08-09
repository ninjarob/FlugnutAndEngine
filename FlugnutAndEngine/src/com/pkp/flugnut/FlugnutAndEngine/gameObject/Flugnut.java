package com.pkp.flugnut.FlugnutAndEngine.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.pkp.flugnut.FlugnutAndEngine.model.level.CarriedObject;
import com.pkp.flugnut.FlugnutAndEngine.screen.global.GameScene;
import com.pkp.flugnut.FlugnutAndEngine.sprites.FlugnutShieldSprite;
import com.pkp.flugnut.FlugnutAndEngine.sprites.FlugnutSprite;
import com.pkp.flugnut.FlugnutAndEngine.utils.GameConstants;
import com.pkp.flugnut.FlugnutAndEngine.utils.Utilities;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Flugnut extends AbstractGameObjectImpl {
    private CarriedObject carriedObject;  //water, bird, other such thing
    private FlugnutSprite flugnutSprite;
    private FlugnutShieldSprite flugnutShieldSprite;

    public Flugnut(GameScene scene, int yOrigForAtlas,int yOrigForAtlasTouch) {
        super(scene, yOrigForAtlas, yOrigForAtlasTouch);
    }

    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        flugnutSprite = new FlugnutSprite(sp.x, sp.y, textureRegion, vertexBufferObjectManager);
        flugnutShieldSprite = new FlugnutShieldSprite(sp.x, sp.y, touchAreaTextureRegion, vertexBufferObjectManager);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        //setup flugnut sprite and body
        Vector2 pos = new Vector2(sp.x- flugnutSprite.getWidth()/2, sp.y- flugnutSprite.getHeight()/2);
        Body flugnutBody = PhysicsFactory.createBoxBody(physics, pos.x, pos.y, flugnutSprite.getWidth(),
                flugnutSprite.getHeight(), BodyDef.BodyType.DynamicBody, GameConstants.FLUGNUT_FIXTURE_DEF);
        flugnutBody.setGravityScale(0);
        flugnutBody.setFixedRotation(true);
        flugnutBody.setLinearDamping(1);
        flugnutSprite.setUserData(flugnutBody);

        //setup flugnutshield sprite and body
        Vector2 pos2 = new Vector2(sp.x- flugnutShieldSprite.getWidth()/2, sp.y- flugnutShieldSprite.getHeight()/2);
        pos = new Vector2(sp.x- flugnutSprite.getWidth()/2, sp.y- flugnutSprite.getHeight()/2);
        Body flugnutShieldBody = PhysicsFactory.createCircleBody(physics, pos.x, pos.y, flugnutShieldSprite.getWidth()/2, 0,
                BodyDef.BodyType.DynamicBody, GameConstants.FLUGNUT_FIXTURE_DEF);
        flugnutShieldBody.setGravityScale(0);
        flugnutShieldBody.setFixedRotation(true);
        MassData shieldMassData = new MassData();
        shieldMassData.mass = .01f;
        shieldMassData.I = 0;
        flugnutBody.setMassData(shieldMassData);
        flugnutShieldBody.setLinearDamping(1);
        flugnutShieldSprite.setUserData(flugnutShieldBody);

        //setup joint, which defines the behavior of flugnut for dragging and such.
        RevoluteJoint rj = Utilities.createRevoluteJoint(flugnutSprite, flugnutShieldSprite, physics, pos2);

        scene.registerTouchArea(flugnutShieldSprite);
        scene.attachChild(flugnutSprite);
        scene.attachChild(flugnutShieldSprite);
        physics.registerPhysicsConnector(new PhysicsConnector(flugnutSprite, flugnutBody, true, true));
        physics.registerPhysicsConnector(new PhysicsConnector(flugnutShieldSprite, flugnutShieldBody, true, true));
    }


    public CarriedObject getCarriedObject() {
        return carriedObject;
    }

    public void setCarriedObject(CarriedObject carriedObject) {
        this.carriedObject = carriedObject;
    }

    public Sprite getSprite() {
        return flugnutSprite;
    }

    public boolean isCarrying() {
        return carriedObject!=null;
    }
}