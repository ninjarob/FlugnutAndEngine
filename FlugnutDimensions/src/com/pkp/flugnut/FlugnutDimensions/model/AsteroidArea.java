package com.pkp.flugnut.FlugnutDimensions.model;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.Vector2;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/4/13
 * Time: 9:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsteroidArea {

    private Vector2 xy1;
    private Vector2 xy2;
    private int asteroidNum;

    public AsteroidArea(Vector2 xy1, Vector2 xy2, int asteroidNum) {
        this.xy1 = xy1;
        this.xy2 = xy2;
        this.asteroidNum = asteroidNum;
    }

    public Vector2 getXy1() {
        return xy1;
    }

    public void setXy1(Vector2 xy1) {
        this.xy1 = xy1;
    }

    public Vector2 getXy2() {
        return xy2;
    }

    public void setXy2(Vector2 xy2) {
        this.xy2 = xy2;
    }

    public int getAsteroidNum() {
        return asteroidNum;
    }

    public void setAsteroidNum(int asteroidNum) {
        this.asteroidNum = asteroidNum;
    }

    public void initAreaOnGame(GameScene gs, PhysicsWorld physicsWorld, VertexBufferObjectManager vertexBufferObjectManager) {
        final Rectangle ground = new Rectangle(xy1.x, xy1.y, xy2.x-xy1.x, 2, vertexBufferObjectManager);
        final Rectangle roof = new Rectangle(xy1.x, xy2.y, xy2.x-xy1.x, 2, vertexBufferObjectManager);
        final Rectangle leftWall = new Rectangle(xy1.x, xy1.y, 2, xy2.y-xy1.y, vertexBufferObjectManager);
        final Rectangle rightWall = new Rectangle(xy2.x, xy1.y, 2, xy2.y-xy1.y, vertexBufferObjectManager);

        PhysicsFactory.createBoxBody(physicsWorld, ground, BodyDef.BodyType.StaticBody, GameConstants.ASTEROID_AREA_WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(physicsWorld, roof, BodyDef.BodyType.StaticBody, GameConstants.ASTEROID_AREA_WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(physicsWorld, leftWall, BodyDef.BodyType.StaticBody, GameConstants.ASTEROID_AREA_WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(physicsWorld, rightWall, BodyDef.BodyType.StaticBody, GameConstants.ASTEROID_AREA_WALL_FIXTURE_DEF);
        gs.attachChild(ground);
        gs.attachChild(roof);
        gs.attachChild(leftWall);
        gs.attachChild(rightWall);
    }
}
