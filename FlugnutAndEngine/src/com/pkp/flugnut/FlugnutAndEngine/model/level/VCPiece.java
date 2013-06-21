package com.pkp.flugnut.FlugnutAndEngine.model.level;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 1/18/13
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class VCPiece { //extends BuildingPiece {
    public boolean engaged;
    public float timeToEngage = 2;
    public float timeSpentEngaging = 0;
    public boolean onFire = false;
    public boolean clicked = false;
    public boolean notScored = true;

    public VCPiece() {}

//    public VCPiece(GLGame game, float startx, float starty, float width, float height, String filename) {
//        super(game, startx, starty, width, height, filename);
//    }
}
