package com.pkp.flugnut.FlugnutDimensions.level;

import com.pkp.flugnut.FlugnutDimensions.model.AsteroidArea;
import com.pkp.flugnut.FlugnutDimensions.model.StationaryBody;

import java.util.List;

/**
 * Contains all information and aspects of a gameSceneInfo that are unique to the gameSceneInfo;
 */
public class GameSceneInfo {
    private List<AsteroidArea> asteroidAreas;
    private List<StationaryBody> stationaryBodies;
    private Integer systemRadius;

	public GameSceneInfo(List<AsteroidArea> asteroidAreas, List<StationaryBody> stationaryBodies, Integer systemRadius) {
        this.asteroidAreas = asteroidAreas;
        this.stationaryBodies = stationaryBodies;
        this.systemRadius = systemRadius;
	}

    public List<AsteroidArea> getAsteroidAreas() {
        return asteroidAreas;
    }

    public void setAsteroidAreas(List<AsteroidArea> asteroidAreas) {
        this.asteroidAreas = asteroidAreas;
    }

    public List<StationaryBody> getStationaryBodies() {
        return stationaryBodies;
    }

    public void setStationaryBodies(List<StationaryBody> stationaryBodies) {
        this.stationaryBodies = stationaryBodies;
    }

    public Integer getSystemRadius() {
        return systemRadius;
    }

    public void setSystemRadius(Integer systemRadius) {
        this.systemRadius = systemRadius;
    }
}