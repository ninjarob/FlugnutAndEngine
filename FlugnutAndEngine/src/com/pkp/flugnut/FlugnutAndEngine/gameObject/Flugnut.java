package com.pkp.flugnut.FlugnutAndEngine.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
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
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Flugnut extends AbstractGameObjectImpl {
    private CarriedObject carriedObject;  //water, bird, other such thing
    private FlugnutSprite flugnutSprite;
    private FlugnutShield flugnutShield;

    public Flugnut(GameScene scene, int yOrigForAtlas,FlugnutShield flugnutShield) {
        super(scene, yOrigForAtlas);
        this.flugnutShield = flugnutShield;
    }

    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        flugnutSprite = new FlugnutSprite(sp.x, sp.y, textureRegion, vertexBufferObjectManager);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        //setup flugnut sprite and body
        Vector2 pos = new Vector2(sp.x- flugnutSprite.getWidth()/2, sp.y- flugnutSprite.getHeight()/2);
        body = PhysicsFactory.createBoxBody(physics, pos.x, pos.y, flugnutSprite.getWidth(),
                flugnutSprite.getHeight(), BodyDef.BodyType.DynamicBody, GameConstants.FLUGNUT_FIXTURE_DEF);
        body.setGravityScale(0);
        body.setFixedRotation(true);
        body.setLinearDamping(1);
        flugnutSprite.setUserData(this);

        scene.attachChild(flugnutSprite);
        physics.registerPhysicsConnector(new PhysicsConnector(flugnutSprite, body, true, true));

        //setup joint, which defines the behavior of flugnut for dragging and such.
        Joint wj = Utilities.createWeldJoint(flugnutSprite, flugnutShield.getSprite(), physics, pos);
        RevoluteJoint rj = Utilities.createRevoluteJoint(flugnutSprite, flugnutShield.getSprite(), physics, flugnutShield.getStartPosition());
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