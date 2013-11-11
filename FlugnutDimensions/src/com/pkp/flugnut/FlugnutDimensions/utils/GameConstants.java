package com.pkp.flugnut.FlugnutDimensions.utils;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import org.andengine.extension.physics.box2d.PhysicsFactory;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/21/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GameConstants {
    // ========================================
    // Collission filtering
    // ========================================
    public static final short CATEGORYBIT_WALL = 1;
    public static final short CATEGORYBIT_FORCE_FIELD = 2;
    public static final short CATEGORYBIT_FLUGNUT = 4;
    public static final short CATEGORYBIT_BUILDING = 8;
    public static final short CATEGORYBIT_BOMB = 16;
    public static final short CATEGORYBIT_ENEMY = 32;
    public static final short CATEGORYBIT_ASTEROID_AREA_WALL = 64;
    public static final short CATEGORYBIT_ASTEROID = 128;

    public static final short MASKBITS_WALL = CATEGORYBIT_WALL + CATEGORYBIT_FLUGNUT + CATEGORYBIT_BUILDING + CATEGORYBIT_BOMB + CATEGORYBIT_ENEMY;
    public static final short MASKBITS_FORCE_FIELD = CATEGORYBIT_BOMB + CATEGORYBIT_ENEMY;
    public static final short MASKBITS_FLUGNUT = CATEGORYBIT_WALL + CATEGORYBIT_ENEMY + CATEGORYBIT_BUILDING;
    public static final short MASKBITS_BUILDING = CATEGORYBIT_WALL + CATEGORYBIT_BOMB + CATEGORYBIT_FLUGNUT;
    public static final short MASKBITS_ENEMY = CATEGORYBIT_WALL + CATEGORYBIT_FORCE_FIELD + CATEGORYBIT_FLUGNUT;
    public static final short MASKBITS_ASTEROID_AREA_WALL = CATEGORYBIT_ASTEROID;
    public static final short MASKBITS_ASTEROID = CATEGORYBIT_ASTEROID_AREA_WALL;

    // =======================================
    // Physics Fixtures
    // =======================================
    public static final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f, false, CATEGORYBIT_WALL, MASKBITS_WALL, (short)0);
    public static final FixtureDef FORCE_FIELD_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.1f, 0.5f, false, CATEGORYBIT_FORCE_FIELD, MASKBITS_FORCE_FIELD, (short)0);
    public static final FixtureDef FLUGNUT_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.1f, 0.5f, false, CATEGORYBIT_FLUGNUT, MASKBITS_FLUGNUT, (short)0);
    public static final FixtureDef BUILDING_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f, false, CATEGORYBIT_BUILDING, MASKBITS_BUILDING, (short)0);
    public static final FixtureDef ENEMY_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f, false, CATEGORYBIT_ENEMY, MASKBITS_ENEMY, (short)0);
    public static final FixtureDef ASTEROID_AREA_WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f, false, CATEGORYBIT_ASTEROID_AREA_WALL, MASKBITS_ASTEROID_AREA_WALL, (short)0);
    public static final FixtureDef ASTEROID_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f, false, CATEGORYBIT_ASTEROID, MASKBITS_ASTEROID, (short)0);


    // ========================================
    // Texts / Labels
    // ========================================
    public static final String EMPTY_STRING = "";
    public static final String LOCKED = "Locked";
    public static final String LABEL_PLAY_BUTTON = "PLAY";
    public static final String LABEL_TUTORIAL_BUTTON = "TUTORIAL";
    public static final String LABEL_HELP_BUTTON = "HELP";
    public static final String LABEL_STORY_BUTTON = "STORY";
    public static final String LABEL_SETTINGS_BUTTON = "SETTINGS";
    public static final String LABEL_QUIT_BUTTON = "QUIT";
    public static final String LABEL_CONNECT_BUTTON = "CONNECT";
    public static final String LABEL_LOGIN_BUTTON = "LOGIN";
    public static final String LABEL_CHECK_SERVER_STATUS_BUTTON = "CHECK SERVER STATUS";
    public static final String LABEL_TEST_SERVER_BUTTON = "TEST SERVER";

    // ========================================
    // Fonts
    // ========================================
    public static final String FONT_DIR = "font/";
    public static final String FONT_DROID = "Droid.ttf";
    public static final String FONT_PLOK = "Plok.ttf";

    // ========================================
    // Images
    // ========================================
    public static final String GRAPHICS_DIR = "gfx/";
    public static final String MAP_FILE = "map.png";
    public static final String BACKGROUND_IMAGE_FILE = "spacebg1-half-noalpha-smaller.gif";
    public static final String PARALLAX_BACKGROUND_FRONT_FILE = "parallax_background_layer_front.png";
    public static final String PARALLAX_BACKGROUND_MID_FILE = "parallax_background_layer_mid.png";
    public static final String SETTING_BUTTONS = "buttons.png";
    public static final String LEVEL_TILE_BUTTONS = "level.png";
    public static final String LEVEL_BUTTONS = "LevelSelect.png";

    // ========================================
    // Music
    // ========================================
    public static final String MUSIC_DIR = "mfx/";
    public static final String MUSIC_DEFAULT_THEME = "flugnutmaintheme.mp3";

    // ========================================
    // Navigation ID's
    // ========================================
    public static final String CASTLE_NAV = "CASTLE_NAV";
    public static final String FORREST_NAV = "FORREST_NAV";
    public static final String VILLAGE_NAV = "VILLAGE_NAV";
    public static final String SEA_NAV = "SEA_NAV";
    public static final String CASTLE_PLANET_BACK_NAV = "CASTLE_PLANET_BACK_NAV";

    // ========================================
    // Global Navigation ID's
    // ========================================
    public static final String MAIN_MENU_NAV = "MAIN_MENU_NAV";
    public static final String PLAY_MENU_NAV = "PLAY_MENU_NAV";
    public static final String HELP_MENU_NAV = "HELP_MENU_NAV";
    public static final String MAP_SCENE_NAV = "MAP_SCENE_NAV";
    public static final String PAUSE_MENU_NAV = "PAUSE_MENU_NAV";
    public static final String SETTINGS_MENU_NAV = "SETTINGS_MENU_NAV";
    public static final String STORY_SCENE_NAV = "STORY_SCENE_NAV";
	public static final String STORY_SCENE_DETAIL_NAV = "STORY_SCENE_DETAIL_NAV";
    public static final String TUTORIAL_MENU_NAV = "TUTORIAL_MENU_NAV";


    public static final int TILE_DIMENSION_X = 97;
    public static final int TILE_DIMENSION_Y = 84;
    public static final int DEFAULT_COLUMNS = 3;
    public static final int DEFAULT_ROWS = 2;
    public static final int DEFAULT_PADDING = 25;
}
