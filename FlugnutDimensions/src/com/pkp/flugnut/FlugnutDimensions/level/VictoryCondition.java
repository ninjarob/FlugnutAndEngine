package com.pkp.flugnut.FlugnutDimensions.level;

/**
 * A condition which should be met before the level is counted as complete.
 */
public interface VictoryCondition {

    public boolean checkVictoryConditionsMet();
    public VCPiece handleVCClick(float x, float y);

    public void executeVictoryEvent();
}
