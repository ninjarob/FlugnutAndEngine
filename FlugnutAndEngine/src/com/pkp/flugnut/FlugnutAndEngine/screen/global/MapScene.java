package com.pkp.flugnut.FlugnutAndEngine.screen.global;

import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
import com.pkp.flugnut.FlugnutAndEngine.screen.navigation.NavigationScene;
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
    private ITextureRegion castleLevel;
    private ITextureRegion forrestLevel;
    private ITextureRegion villageLevel;
    private ITextureRegion seaLevel;

    // ===========================================================
    // CONSTANTS
    // ===========================================================

    private final int CASTLE_NAV_X = new BigDecimal(GLGame.CAMERA_WIDTH - (GLGame.CAMERA_WIDTH * 0.65)).intValue();
    private final int CASTLE_NAV_Y = new BigDecimal(GLGame.CAMERA_HEIGHT - (GLGame.CAMERA_HEIGHT * 0.99)).intValue();
    private final int FORREST_NAV_X = new BigDecimal(GLGame.CAMERA_WIDTH - (GLGame.CAMERA_WIDTH * 0.90)).intValue();
    private final int FORREST_NAV_Y = new BigDecimal(GLGame.CAMERA_HEIGHT - (GLGame.CAMERA_HEIGHT * 0.60)).intValue();
    private final int VILLAGE_NAV_X = new BigDecimal(GLGame.CAMERA_WIDTH - (GLGame.CAMERA_WIDTH * 0.45)).intValue();
    private final int VILLAGE_NAV_Y = new BigDecimal(GLGame.CAMERA_HEIGHT - (GLGame.CAMERA_HEIGHT * 0.75)).intValue();
    private final int SEA_NAV_X = new BigDecimal(GLGame.CAMERA_WIDTH - (GLGame.CAMERA_WIDTH * 0.30)).intValue();
    private final int SEA_NAV_Y = new BigDecimal(GLGame.CAMERA_HEIGHT - (GLGame.CAMERA_HEIGHT * 0.30)).intValue();
    private final int BACK_BUTTON_NAV_X = new BigDecimal(GLGame.CAMERA_WIDTH - (GLGame.CAMERA_WIDTH * 0.95)).intValue();
    private final int BACK_BUTTON_NAV_Y = new BigDecimal(GLGame.CAMERA_HEIGHT - (GLGame.CAMERA_WIDTH * 0.10)).intValue();

    public MapScene(GLGame game) {
        super(game);
    }

    @Override
    public void initResources() {
        //IMAGES
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GameConstants.GRAPHICS_DIR);
        //BACKGROUND
        BitmapTextureAtlas backgroundBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 800, 1200);
        this.mapBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundBitmapTextureAtlas, game, GameConstants.MAP_FILE, 0, 0);
        backgroundBitmapTextureAtlas.load();


        BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 129, 226, TextureOptions.BILINEAR);

        // Flugnut's Planet Level Image
        this.castleLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.LEVEL_BUTTONS, 0, 193);
        bitmapTextureAtlas.load();

        // Forrest Level Image
        //TODO: Need image for navigating into forrest
        this.forrestLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.LEVEL_BUTTONS, 0, 193);
        bitmapTextureAtlas.load();

        // Houses Level Image
        //TODO: Need image for navigating into house
        this.villageLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.LEVEL_BUTTONS, 0, 193);
        bitmapTextureAtlas.load();

        // Pond Level Image
        //TODO: Need image for navigating into pond
        this.seaLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.LEVEL_BUTTONS, 0, 193);
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
        //Castle Nav
        configureSprite(CASTLE_NAV_X,CASTLE_NAV_Y,castleLevel,GameConstants.CASTLE_NAV);
        //Forrest Level Nav
        configureSprite(FORREST_NAV_X,FORREST_NAV_Y,forrestLevel,GameConstants.FORREST_NAV);
        //Village Nav
        configureSprite(VILLAGE_NAV_X,VILLAGE_NAV_Y,villageLevel,GameConstants.VILLAGE_NAV);
        //Sea Nav
        configureSprite(SEA_NAV_X,SEA_NAV_Y,seaLevel,GameConstants.SEA_NAV);

        //BACK_TO_MAIN_MENU BUTTON
        final Sprite backButton = game.getNavigationElements().getBackToMainMenuButton(BACK_BUTTON_NAV_X, BACK_BUTTON_NAV_Y, game, defaultObjectManager);
        attachChild(backButton);
        registerTouchArea(backButton);

        setTouchAreaBindingOnActionDownEnabled(true);
    }

    private void configureSprite(final int x,final int y,final ITextureRegion navButton,final String redirectId) {
        final Sprite castleNav = new Sprite(x, y, navButton, defaultObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                NavigationScene toNavigate = (NavigationScene) NavigationRedirect.getInstance().getObjectToNavigate(redirectId, game);
                if(!toNavigate.hasCompletedPreviousScene()){
                    //TODO : Something needs to happen here, a popup saying 'Complete Forrest Level to unlock this!!' or something?
                    return true;
                }
                game.setNewScene(toNavigate);
                return true;
            }
        };
        attachChild(castleNav);
        registerTouchArea(castleNav);
    }
}
