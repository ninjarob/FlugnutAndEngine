package com.pkp.flugnut.FlugnutAndEngine.gameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.pkp.flugnut.FlugnutAndEngine.screen.global.GameScene;
import com.pkp.flugnut.FlugnutAndEngine.sprites.FlugnutShieldSprite;
import com.pkp.flugnut.FlugnutAndEngine.utils.GameConstants;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class FlugnutShield extends AbstractGameObjectImpl {
    private Flugnut flugnut;
    private FlugnutShieldSprite flugnutShieldSprite;

    public FlugnutShield(GameScene scene, int yOrigForAtlas) {
        super(scene, yOrigForAtlas);
    }

    public Flugnut getFlugnut() {
        return flugnut;
    }

    public void setFlugnut(Flugnut flugnut) {
        this.flugnut = flugnut;
    }

    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        flugnutShieldSprite = new FlugnutShieldSprite(sp.x, sp.y, textureRegion, vertexBufferObjectManager);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        //setup flugnut sprite and body
        Vector2 pos = new Vector2(sp.x- flugnutShieldSprite.getWidth()/2, sp.y- flugnutShieldSprite.getHeight()/2);
        body = PhysicsFactory.createBoxBody(physics, pos.x, pos.y, flugnutShieldSprite.getWidth(),
                flugnutShieldSprite.getHeight(), BodyDef.BodyType.DynamicBody, GameConstants.FLUGNUT_FIXTURE_DEF);
        MassData shieldMassData = new MassData();
        shieldMassData.mass = 0f;
        shieldMassData.I = 0;
        body.setGravityScale(0);
        body.setFixedRotation(true);
        body.setMassData(shieldMassData);
        body.setFixedRotation(true);
        body.setLinearDamping(.5f);
        flugnutShieldSprite.setUserData(this);
        scene.registerTouchArea(flugnutShieldSprite);
        scene.attachChild(flugnutShieldSprite);

        physics.registerPhysicsConnector(new PhysicsConnector(flugnutShieldSprite, body, true, true));
    }

    public Sprite getSprite() {
        return flugnutShieldSprite;
    }
}