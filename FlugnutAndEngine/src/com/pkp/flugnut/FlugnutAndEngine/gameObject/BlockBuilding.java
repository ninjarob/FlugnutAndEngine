package com.pkp.flugnut.FlugnutAndEngine.gameObject;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.pkp.flugnut.FlugnutAndEngine.screen.global.GameScene;
import com.pkp.flugnut.FlugnutAndEngine.utils.GameConstants;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BlockBuilding extends AbstractGameObjectImpl {

    private int health;
    private int maxHealth;
    private Sprite buildingSprite;

    public BlockBuilding(GameScene scene, int yOrigForAtlas, int health, int maxHealth) {
        super(scene, yOrigForAtlas);
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public BlockBuilding(GameScene scene, int yOrigForAtlas, int scaledWidth, int scaledHeight, int health, int maxHealth) {
        super(scene, yOrigForAtlas, scaledWidth, scaledHeight);
        this.health = health;
        this.maxHealth = maxHealth;
    }


    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        buildingSprite = new Sprite(sp.x, sp.y, scaledWidth, scaledHeight, textureRegion, vertexBufferObjectManager);
    }

    //Block Buildings start point is in the lower left
    @Override
    public void initForScene(PhysicsWorld physics) {
        Body buildingBody = PhysicsFactory.createBoxBody(physics, sp.x, sp.y, buildingSprite.getWidthScaled(),
                buildingSprite.getHeightScaled(), BodyDef.BodyType.DynamicBody, GameConstants.FLUGNUT_FIXTURE_DEF);
        MassData md  = new MassData();
        md.mass = 1f;
        md.I = 0;
        buildingBody.setMassData(md);
        buildingBody.setFixedRotation(true);
        buildingBody.setLinearDamping(1);
        buildingSprite.setUserData(buildingBody);

        scene.attachChild(buildingSprite);
        scene.registerTouchArea(buildingSprite);
        physics.registerPhysicsConnector(new PhysicsConnector(buildingSprite, buildingBody, true, true));
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Sprite getSprite() {
        return buildingSprite;
    }

//    public BuildingPiece handleObjClick(float x, float y) {
//        BuildingPiece returnPiece = null;
//        boolean itemClicked = false;
//        for (BuildingPiece bp : buildingPieces) {
//            if (bp.clickable) {
//                float dist = Utilities.dist(bp.body.getPosition(), x, y);
//                if (dist < 15 && !itemClicked) {
//                    itemClicked = true;
//                    bp.click(true);
//                    returnPiece = bp;
//                }
//                else {
//                    if (bp.clicked) {
//                        bp.click(false);
//                    }
//                }
//            }
//        }
//        return returnPiece;
//    }
}