package com.pkp.flugnut.FlugnutDimensions.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.pkp.flugnut.FlugnutDimensions.gameObject.Asteroid;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/13/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsteroidInfo {

    private int id;
    private Asteroid asteroid;
    private Vector2 pos;
    private Vector2 vel;
    private int hp;
    private int type;
    private Vector2 gravCenter;
    private Joint pullJoint;

    public AsteroidInfo(int id, Vector2 pos, Vector2 vel, Vector2 gravCenter, int hp, int type) {
        this.id = id;
        this.pos = pos;
        this.vel = vel;
        this.hp = hp;
        this.type = type;
        this.gravCenter = gravCenter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Asteroid getAsteroid() {
        return asteroid;
    }

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getVel() {
        return vel;
    }

    public void setVel(Vector2 vel) {
        this.vel = vel;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Vector2 getGravCenter() {
        return gravCenter;
    }

    public void setGravCenter(Vector2 gravCenter) {
        this.gravCenter = gravCenter;
    }

    public Joint getPullJoint() {
        return pullJoint;
    }

    public void setPullJoint(Joint pullJoint) {
        this.pullJoint = pullJoint;
    }
}
