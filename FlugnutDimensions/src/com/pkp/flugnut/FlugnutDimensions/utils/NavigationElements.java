package com.pkp.flugnut.FlugnutDimensions.utils;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.BaseGameScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/25/13
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class NavigationElements {

    private ITextureRegion backButtonTextureRegion;
    private ITextureRegion levelTileTextureRegion;

    Map<NavigationButtons, Sprite> navigationButtonCache;


    public NavigationElements() {
        navigationButtonCache = new HashMap<NavigationButtons, Sprite>(1);
    }

    public enum NavigationButtons {
        LEVEL_BUTTON,
        BACK_BUTTON,
        BACK_TO_MAIN_MENU;
    }

    public void getBackButtonResources(GLGame game) {
        BitmapTextureAtlas navigationBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 129, 226, TextureOptions.BILINEAR);
        this.backButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(navigationBitmapTextureAtlas, game, GameConstants.SETTING_BUTTONS, 0, 0);
        this.backButtonTextureRegion = TextureRegionFactory.extractFromTexture(navigationBitmapTextureAtlas, 64, 64, 64, 64);  //Cropping the buttons image to get the back button
        navigationBitmapTextureAtlas.load();
    }


    public ITextureRegion getLevelTileResources(GLGame game) {
        BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 97, 84, TextureOptions.BILINEAR);
        this.levelTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, game, GameConstants.LEVEL_TILE_BUTTONS, 0, 0);
        this.levelTileTextureRegion = TextureRegionFactory.extractFromTexture(bitmapTextureAtlas);  //Cropping the buttons image to get the back button
        bitmapTextureAtlas.load();
        return levelTileTextureRegion;
    }

    public Sprite getBackToMainMenuButton(final int x, final int y, final GLGame game, final VertexBufferObjectManager objectManager) {
        if (navigationButtonCache.containsKey(NavigationButtons.BACK_TO_MAIN_MENU)) {
            return navigationButtonCache.get(NavigationButtons.BACK_TO_MAIN_MENU);
        }

        if (backButtonTextureRegion == null) {
            getBackButtonResources(game);
        }

        final Sprite backToMainButton = new Sprite(x, y, backButtonTextureRegion, objectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()) {
                    game.setNewScene((BaseGameScene) NavigationRedirect.getInstance().getObjectToNavigate(GameConstants.MAIN_MENU_NAV, game));
                    return true;
                }
                return false;
            }
        };

        navigationButtonCache.put(NavigationButtons.BACK_TO_MAIN_MENU, backToMainButton);
        return backToMainButton;
    }

    public Sprite getBackButton(final int x, final int y, final GLGame game, final String navigationId) {
        if (backButtonTextureRegion == null) {
            getBackButtonResources(game);
        }

        final Sprite backButton = new Sprite(x, y, backButtonTextureRegion, game.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()) {
                    game.setNewScene((BaseGameScene) NavigationRedirect.getInstance().getObjectToNavigate(navigationId, game));
                    return true;
                }
                return false;
            }
        };
        return backButton;
    }
}
