package com.pkp.flugnut.FlugnutDimensions.model;

import com.pkp.flugnut.FlugnutDimensions.gameObject.Ship;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/13/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerInfo {

    private String username;
    private Ship ship;

    public PlayerInfo(String username) {
        this.username = username;
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
}
