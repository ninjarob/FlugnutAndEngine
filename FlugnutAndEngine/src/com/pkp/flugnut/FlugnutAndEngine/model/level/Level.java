package com.pkp.flugnut.FlugnutAndEngine.model.level;

import org.andengine.entity.sprite.Sprite;

/**
 * Contains all information and aspects of a level that are unique to all levels.
 */
public class Level {
    public boolean locked;
    public boolean weapons;
    public String levelId;
    public LevelScore score = LevelScore.ZERO_STAR;
    public Sprite levelSprite;
    public String gameSceneName;
    public String preCondition;

    public enum LevelScore {
        ZERO_STAR(0),
        ONE_STAR(1000),
        TWO_STAR(2000),
        THREE_STAR(3000);

        private int points;

        private LevelScore(int score) {
            this.points = score;
        }

        public int getPoints() {
            return points;
        }
    }

    //Level Info
    public int highScore;
    public int totalScore;
    public float timeLimit;

    public Level(String levelId, boolean weapons, boolean locked, String gameSceneName, String preCondition) {
        this.levelId = levelId;
        this.weapons = weapons;
        this.locked = locked;
        this.gameSceneName = gameSceneName;
        this.preCondition = preCondition;
    }

    public Level(String levelId, Sprite levelSprite) {
        this.levelId = levelId;
        this.levelSprite = levelSprite;
    }
}