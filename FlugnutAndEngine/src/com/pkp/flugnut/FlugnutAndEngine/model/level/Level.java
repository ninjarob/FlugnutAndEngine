package com.pkp.flugnut.FlugnutAndEngine.model.level;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all information and aspects of a level that are unique to the level;
 */
public class Level {
	public boolean locked;
    public boolean weapons;
    public List<VictoryCondition> victoryConditions;
    public LevelOnMap levelOnMap;
    public String bgFileName;

    //Level Info
    public int oneSymbolScore=1000;
    public int twoSymbolScore=2000;
    public int threeSymbolScore=3000;
    public int highScore;
    public float timeLimit;

	
	public Level() {
        victoryConditions = new ArrayList<VictoryCondition>();
        bgFileName = "spacebg1-half-noalpha-smaller.gif";
	}

	public void initLevel()
	{

	}

    public boolean victory() {
        for (VictoryCondition v : victoryConditions) {
            if (!v.checkVictoryConditionsMet()) return false;
        }
        return true;
    }
}