package com.pkp.flugnut.FlugnutDimensions.model;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.gameObject.Ship;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/13/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerInfo {

    private int id;
    private String username;
    private Ship ship;
    private Vector2 pos;

    public PlayerInfo(int id, String username, Vector2 pos) {
        this.id = id;
        this.username = username;
        this.pos = pos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }
}
