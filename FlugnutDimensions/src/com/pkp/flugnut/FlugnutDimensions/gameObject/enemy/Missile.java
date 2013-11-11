package com.pkp.flugnut.FlugnutDimensions.gameObject.enemy;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import com.pkp.flugnut.FlugnutDimensions.gameObject.AbstractGameObjectImpl;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Missile extends AbstractGameObjectImpl {

    private int health;
    private int maxHealth;
    private Sprite missileSprite;

    public Missile(GLGame game, GameScene scene, TextureInfoHolder tih, int health, int maxHealth) {
        super(game, scene, tih);
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public Missile(GLGame game, GameScene scene, TextureInfoHolder tih, int scaledWidth, int scaledHeight, int health, int maxHealth) {
        super(game, scene, tih, scaledWidth, scaledHeight);
        this.health = health;
        this.maxHealth = maxHealth;
    }


    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        missileSprite = new Sprite(sp.x, sp.y, scaledWidth, scaledHeight, textureRegion, vertexBufferObjectManager);
    }

    //Block Buildings start point is in the lower left
    @Override
    public void initForScene(PhysicsWorld physics) {
        Body buildingBody = PhysicsFactory.createBoxBody(physics, sp.x, sp.y, missileSprite.getWidthScaled(),
                missileSprite.getHeightScaled(), BodyDef.BodyType.DynamicBody, GameConstants.FLUGNUT_FIXTURE_DEF);
        MassData md  = new MassData();
        md.mass = 1f;
        md.I = 0;
        buildingBody.setMassData(md);
        buildingBody.setFixedRotation(true);
        buildingBody.setLinearDamping(1);
        missileSprite.setUserData(buildingBody);

        scene.attachChild(missileSprite);
        scene.registerTouchArea(missileSprite);
        physics.registerPhysicsConnector(new PhysicsConnector(missileSprite, buildingBody, true, true));
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Sprite getSprite() {
        return missileSprite;
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

    public void onActionDown(float touchX, float touchY, PhysicsWorld physicsWorld) {

    }
}