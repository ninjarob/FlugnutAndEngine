package com.pkp.flugnut.FlugnutDimensions.model;

import com.pkp.flugnut.FlugnutDimensions.gameObject.CelestialBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/4/13
 * Time: 8:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpaceGameMap {

    private List<CelestialBody> stationaryBodies;
    private List<AsteroidArea> asteroidAreas;
    private int systemRadius;

    public SpaceGameMap(List<CelestialBody> stationaryBodies, List<AsteroidArea> asteroidAreas, int systemRadius) {
        this.stationaryBodies = stationaryBodies;
        this.asteroidAreas = asteroidAreas;
        this.systemRadius = systemRadius;
    }

    public List<AsteroidArea> getAsteroidAreas() {
        return asteroidAreas;
    }

    public void setAsteroidAreas(List<AsteroidArea> asteroidAreas) {
        this.asteroidAreas = asteroidAreas;
    }

    public List<CelestialBody> getStationaryBodies() {
        return stationaryBodies;
    }

    public void setStationaryBodies(List<CelestialBody> stationaryBodies) {
        this.stationaryBodies = stationaryBodies;
    }

    public int getSystemRadius() {
        return systemRadius;
    }

    public void setSystemRadius(int systemRadius) {
        this.systemRadius = systemRadius;
    }
}
