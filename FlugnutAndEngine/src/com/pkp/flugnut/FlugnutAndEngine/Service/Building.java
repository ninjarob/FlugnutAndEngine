package com.pkp.flugnut.FlugnutAndEngine.Service;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
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

public class Building {
//	public List<BuildingPiece> buildingPieces;
	public int maxHealth;
    public int health;
    private ITextureRegion textureRegion;
    private GameScene scene;


    public Building(GameScene scene, int health) {
        this.health = health;
        this.maxHealth = health;
        this.scene = scene;
        //buildingPieces = new ArrayList<BuildingPiece>();
	}


    public void initResources(String filename, BitmapTextureAtlas mBitmapTextureAtlas, int startYPosition) {
        this.textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, scene.game, filename, 0, startYPosition);
    }

    public void initForScene(float x, float y, VertexBufferObjectManager vertexBufferObjectManager, PhysicsWorld physics) {
        final Body body;
        Sprite sprite = new Sprite(x, y, textureRegion, vertexBufferObjectManager);

        Vector2 pos = new Vector2(x-sprite.getWidth()/2, y-sprite.getHeight()/2);
        body = PhysicsFactory.createBoxBody(physics, pos.x, pos.y, sprite.getWidth() * 2, sprite.getHeight() * 2, BodyDef.BodyType.StaticBody, GameConstants.FLUGNUT_FIXTURE_DEF);
        sprite.setUserData(body);
        scene.attachChild(sprite);
        physics.registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true));
    }

    public ITextureRegion getTextureRegion() {
        return textureRegion;
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