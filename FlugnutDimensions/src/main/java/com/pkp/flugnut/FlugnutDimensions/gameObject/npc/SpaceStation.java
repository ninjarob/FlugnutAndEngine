package com.pkp.flugnut.FlugnutDimensions.gameObject.npc;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/11/13
 * Time: 7:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpaceStation extends CelestialBody {
    private int width;
    private int height;
    private float rotation;

    public SpaceStation(GLGame game, TextureInfoHolder tih, int id, Vector2 location, int width, int height, float rotation) {
        super(game, tih, id, location);
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
