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

        /*Ships*/      GAWAIN(2,1), GAWAIN_ENGINE(2,2),

        /*Stars*/      SUN(3,1),

        /*Planets*/    MERCURY(4,1), VENUS(4,2), EARTH(4,3), MARS(4,4), JUPITER(4,5), SATURN(4,6), URANUS(4,7), NEPTUNE(4,8), PLUTO(4,9), EROS(4,10),

        /*Asteroids*/  ASTEROID1(5,1), LARGE_ASTEROID1(5,2);

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

    public static TextureType getTextureType(int category, int type) {
        switch(category) {
            case 1:
                switch(type) {
                    case 1:
                        return BUTTONS;
                    case 2:
                        return THROTTLE;
                    case 3:
                        return THROTTLE_IND;
                    case 4:
                        return THROTTLE_BUTTON;
                }
                break;
            case 2:
                switch(type) {
                    case 1:
                        return GAWAIN;
                }
            case 3:
                switch(type) {
                    case 1:
                        return SUN;
                }
            case 4:
                switch(type) {
                    case 1:
                        return MERCURY;
                    case 2:
                        return VENUS;
                    case 3:
                        return EARTH;
                    case 4:
                        return MARS;
                    case 5:
                        return JUPITER;
                    case 6:
                        return SATURN;
                    case 7:
                        return URANUS;
                    case 8:
                        return NEPTUNE;
                    case 9:
                        return PLUTO;
                    case 10:
                        return EROS;

                }
            case 5:
                switch(type) {
                    case 1:
                        return ASTEROID1;
                    case 2:
                        return LARGE_ASTEROID1;
                }
        }
        return null;
    }
}
