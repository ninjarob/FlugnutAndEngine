package com.pkp.flugnut.FlugnutDimensions.model;

import org.andengine.entity.primitive.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/4/13
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class StationaryBody {
    private Vector2 location;
    private int bodyType;
    private int radius;

    public StationaryBody(Vector2 location, int bodyType, int radius) {
        this.location = location;
        this.bodyType = bodyType;
        this.radius = radius;
    }

    public Vector2 getLocation() {
        return location;
    }

    public void setLocation(Vector2 location) {
        this.location = location;
    }

    public int getBodyType() {
        return bodyType;
    }

    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

}
