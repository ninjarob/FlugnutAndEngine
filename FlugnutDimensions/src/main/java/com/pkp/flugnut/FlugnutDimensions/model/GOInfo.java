package com.pkp.flugnut.FlugnutDimensions.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 12/7/13
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class GOInfo {

    private int id;
    private Vector2 pos;
    private float velMag;
    private int type;

    public GOInfo(int id, Vector2 pos, float velMag, int type) {
        this.id = id;
        this.pos = pos;
        this.velMag = velMag;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public float getVelMag() {
        return velMag;
    }

    public void setVelMag(float velMag) {
        this.velMag = velMag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
