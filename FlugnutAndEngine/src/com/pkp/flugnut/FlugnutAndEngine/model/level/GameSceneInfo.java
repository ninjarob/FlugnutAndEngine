package com.pkp.flugnut.FlugnutAndEngine.model.level;

/**
 * Contains all information and aspects of a gameSceneInfo that are unique to the gameSceneInfo;
 */
public class GameSceneInfo {
	private boolean locked;
    private boolean weaponEnabled;
    //private List<VictoryCondition> victoryConditions;
    //private LevelOnMap levelOnMap;
    private String bgFileName;

	public GameSceneInfo() {
        //victoryConditions = new ArrayList<VictoryCondition>();
        //bgFileName = "spacebg1-half-noalpha-smaller.gif";
	}

	public void initLevel()
	{

	}

    public boolean victory() {
//        for (VictoryCondition v : victoryConditions) {
//            if (!v.checkVictoryConditionsMet()) return false;
//        }
        return true;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean  isWeaponEnabled() {
        return weaponEnabled;
    }

    public void setWeaponEnabled(boolean weaponEnabled) {
        this.weaponEnabled = weaponEnabled;
    }

    public String getBgFileName() {
        return bgFileName;
    }

    public void setBgFileName(String bgFileName) {
        this.bgFileName = bgFileName;
    }
}