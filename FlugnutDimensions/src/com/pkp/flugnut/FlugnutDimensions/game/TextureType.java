package com.pkp.flugnut.FlugnutDimensions.game;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/9/13
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
public enum TextureType {

        /*Misc*/       BUTTONS(1, 1), THROTTLE(1,2), THROTTLE_IND(1,3), THROTTLE_BUTTON(1,4),

        /*Ships*/      GAWAIN(2,1),

        /*Stars*/      SUN(3,1),

        /*Planets*/    EARTH(4,1),

        /*Asteroids*/  ASTEROID1(5,1);

    private final int category;
    private final int type;
    TextureType(int category, int type) {
        this.category = category;
        this.type = type;
    }

    public int getCategory() {
        return category;
    }

    public int getType() {
        return type;
    }
}
