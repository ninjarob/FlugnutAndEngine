package com.pkp.flugnut.FlugnutDimensions.gameObject.npc;


import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/11/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Planet extends CelestialBody {
    private int radius;

    public Planet(GLGame game, TextureInfoHolder tih, int id, Vector2 location) {
        super(game, tih, id, location);
        this.radius = tih.getHeight();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
