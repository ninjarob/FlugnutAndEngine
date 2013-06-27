package com.pkp.flugnut.FlugnutAndEngine.screen;

import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
import com.pkp.flugnut.FlugnutAndEngine.utils.GameConstants;
import com.pkp.flugnut.FlugnutAndEngine.utils.NavigationRedirect;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.math.BigDecimal;

public class MapScene extends BaseGameScene {

    // ===========================================================
    // Fields
    // ===========================================================
    private SpriteBackground normalBackground;

    private BitmapTextureAtlas backgroundBitmapTextureAtlas;
    private BitmapTextureAtlas bitmapTextureAtlas;

    private ITextureRegion mapBackground;
    private ITextureRegion flugnutPlanetLevel;
    private ITextureRegion forrestLevel;
    private ITextureRegion housesLevel;
    private ITextureRegion pondLevel;

    private Font mFont;

    private VertexBufferObjectManager objectManager;

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
        //FONT
        FontFactory.setAssetBasePath(GameConstants.ASSET_FONT_DIR);
        final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.mFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(), GameConstants.ASSET_FONT_DROID, 16, true, android.graphics.Color.WHITE);
        this.mFont.load();

        //IMAGES
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GameConstants.ASSET_GRAPHICS_DIR);
        //BACKGROUND
        this.backgroundBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 800, 1200);
        this.mapBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundBitmapTextureAtlas, game, GameConstants.ASSET_MAP_FILE, 0, 0);
        this.backgroundBitmapTextureAtlas.load();


        this.bitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 129, 226, TextureOptions.BILINEAR);

        // Flugnut's Planet Level Image
        this.flugnutPlanetLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.ASSET_LEVEL_BUTTONS, 0, 193);
        this.bitmapTextureAtlas.load();

        // Forrest Level Image
        //TODO: Need image for navigating into forrest
        this.forrestLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.ASSET_LEVEL_BUTTONS, 0, 193);
        this.bitmapTextureAtlas.load();

        // Houses Level Image
        //TODO: Need image for navigating into house
        this.housesLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.ASSET_LEVEL_BUTTONS, 0, 193);
        this.bitmapTextureAtlas.load();

        // Pond Level Image
        //TODO: Need image for navigating into pond
        this.pondLevel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.ASSET_LEVEL_BUTTONS, 0, 193);
        this.bitmapTextureAtlas.load();
    }

    @Override
    public void initScene() {

        objectManager = game.getVertexBufferObjectManager();

        //BACKGROUND
        normalBackground = new SpriteBackground(0, 0, 0, new Sprite(0, 0, mapBackground, objectManager));
        setBackground(normalBackground);

        buildNavigationControl();
    }

    private void buildNavigationControl() {

        //Home Planet Nav
        final Sprite homePlanetNav = new Sprite(HOME_PLANET_NAV_X, HOME_PLANET_NAV_Y, flugnutPlanetLevel, objectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                game.setNewScene((BaseGameScene) NavigationRedirect.getInstance().getObjectToNavigate(GameConstants.HOME_PLANET_NAV, game));
                return true;
            }
        };
        attachChild(homePlanetNav);
        registerTouchArea(homePlanetNav);

        //Forrest Level Nav
        final Sprite forrestLevelNav = new Sprite(FORREST_NAV_X, FORREST_NAV_Y, forrestLevel, objectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                //game.setNewScene(new MainMenuScene(game));
                return true;
            }
        };
        attachChild(forrestLevelNav);
        registerTouchArea(forrestLevelNav);

        //Houses Nav
        final Sprite housesLevelNav = new Sprite(HOUSES_NAV_X, HOUSES_NAV_Y, housesLevel, objectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                //game.setNewScene(new MainMenuScene(game));
                return true;
            }
        };
        attachChild(housesLevelNav);
        registerTouchArea(housesLevelNav);

        //Pond Nav
        final Sprite pondLevelNav = new Sprite(POND_NAV_X, POND_NAV_Y, pondLevel, objectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                //game.setNewScene(new MainMenuScene(game));
                return true;
            }
        };
        attachChild(pondLevelNav);
        registerTouchArea(pondLevelNav);


        //BACK_TO_MAIN_MENU BUTTON
        final Sprite backButton = game.getNavigationElements().getBackToMainMenuButton(BACK_BUTTON_NAV_X, BACK_BUTTON_NAV_Y, game, objectManager);
        attachChild(backButton);
        registerTouchArea(backButton);


        setTouchAreaBindingOnActionDownEnabled(true);
    }
}
