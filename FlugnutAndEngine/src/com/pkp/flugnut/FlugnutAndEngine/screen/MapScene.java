package com.pkp.flugnut.FlugnutAndEngine.screen;

import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
import com.pkp.flugnut.FlugnutAndEngine.utils.GameConstants;
import com.pkp.flugnut.FlugnutAndEngine.utils.NavigationRedirect;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.math.BigDecimal;

public class MapScene extends BaseGameScene {

    // ===========================================================
    // Fields
    // ===========================================================

    private ITextureRegion mapBackground;
    private ITextureRegion flugnutPlanetLevel;
    private ITextureRegion forrestLevel;
    private ITextureRegion housesLevel;
    private ITextureRegion pondLevel;

    // ===========================================================
    // CONSTANTS
    // ===========================================================

    private final int HOME_PLANET_NAV_X = new BigDecimal(GLGame.CAMERA_WIDTH - (GLGame.CAMERA_WIDTH * 0.65)).intValue();
    private final int HOME_PLANET_NAV_Y = new BigDecimal(GLGame.CAMERA_HEIGHT - (GLGame.CAMERA_HEIGHT * 0.99)).intValue();
    private final int FORREST_NAV_X = new BigDecimal(GLGame.CAMERA_WIDTH - (GLGame.CAMERA_WIDTH * 0.90)).intValue();
    private final int FORREST_NAV_Y = new BigDecimal(GLGame.CAMERA_HEIGHT - (GLGame.CAMERA_HEIGHT * 0.60)).intValue();
    private final int HOUSES_NAV_X = new BigDecimal(GLGame.CAMERA_WIDTH - (GLGame.CAMERA_WIDTH * 0.45)).intValue();
    private final int HOUSES_NAV_Y = new BigDecimal(GLGame.CAMERA_HEIGHT - (GLGame.CAMERA_HEIGHT * 0.75)).intValue();
    private final int POND_NAV_X = new BigDecimal(GLGame.CAMERA_WIDTH - (GLGame.CAMERA_WIDTH * 0.30)).intValue();
    private final int POND_NAV_Y = new BigDecimal(GLGame.CAMERA_HEIGHT - (GLGame.CAMERA_HEIGHT * 0.30)).intValue();
    private final int BACK_BUTTON_NAV_X = new BigDecimal(GLGame.CAMERA_WIDTH - (GLGame.CAMERA_WIDTH * 0.95)).intValue();
    private final int BACK_BUTTON_NAV_Y = new BigDecimal(GLGame.CAMERA_HEIGHT - (GLGame.CAMERA_WIDTH * 0.10)).intValue();

    public MapScene(GLGame game) {
        super(game);
    }

    @Override
    public void initResources() {
        //IMAGES
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GameConstants.ASSET_GRAPHICS_DIR);
        //BACKGROUND
        BitmapTextureAtlas backgroundBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 800, 1200);
        this.mapBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundBitmapTextureAtlas, game, GameConstants.ASSET_MAP_FILE, 0, 0);
        backgroundBitmapTextureAtlas.load();


        BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 129, 226, TextureOptions.BILINEAR);

        // Flugnut's Planet Level Image
        this.flugnutPlanetLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.ASSET_LEVEL_BUTTONS, 0, 193);
        bitmapTextureAtlas.load();

        // Forrest Level Image
        //TODO: Need image for navigating into forrest
        this.forrestLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.ASSET_LEVEL_BUTTONS, 0, 193);
        bitmapTextureAtlas.load();

        // Houses Level Image
        //TODO: Need image for navigating into house
        this.housesLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.ASSET_LEVEL_BUTTONS, 0, 193);
        bitmapTextureAtlas.load();

        // Pond Level Image
        //TODO: Need image for navigating into pond
        this.pondLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.ASSET_LEVEL_BUTTONS, 0, 193);
        bitmapTextureAtlas.load();
    }

    @Override
    public void initScene() {

        //BACKGROUND
        SpriteBackground mapSpriteBackground = new SpriteBackground(0, 0, 0, new Sprite(0, 0, mapBackground, defaultObjectManager));
        setBackground(mapSpriteBackground);

        buildNavigationControl();
    }

    private void buildNavigationControl() {

        //Home Planet Nav
        final Sprite homePlanetNav = new Sprite(HOME_PLANET_NAV_X, HOME_PLANET_NAV_Y, flugnutPlanetLevel, defaultObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                game.setNewScene((BaseGameScene) NavigationRedirect.getInstance().getObjectToNavigate(GameConstants.HOME_PLANET_NAV, game));
                return true;
            }
        };
        attachChild(homePlanetNav);
        registerTouchArea(homePlanetNav);

        //Forrest Level Nav
        final Sprite forrestLevelNav = new Sprite(FORREST_NAV_X, FORREST_NAV_Y, forrestLevel, defaultObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                //game.setNewScene(new MainMenuScene(game));
                return true;
            }
        };
        attachChild(forrestLevelNav);
        registerTouchArea(forrestLevelNav);

        //Houses Nav
        final Sprite housesLevelNav = new Sprite(HOUSES_NAV_X, HOUSES_NAV_Y, housesLevel, defaultObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                //game.setNewScene(new MainMenuScene(game));
                return true;
            }
        };
        attachChild(housesLevelNav);
        registerTouchArea(housesLevelNav);

        //Pond Nav
        final Sprite pondLevelNav = new Sprite(POND_NAV_X, POND_NAV_Y, pondLevel, defaultObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                //game.setNewScene(new MainMenuScene(game));
                return true;
            }
        };
        attachChild(pondLevelNav);
        registerTouchArea(pondLevelNav);


        //BACK_TO_MAIN_MENU BUTTON
        final Sprite backButton = game.getNavigationElements().getBackToMainMenuButton(BACK_BUTTON_NAV_X, BACK_BUTTON_NAV_Y, game, defaultObjectManager);
        attachChild(backButton);
        registerTouchArea(backButton);


        setTouchAreaBindingOnActionDownEnabled(true);
    }
}
