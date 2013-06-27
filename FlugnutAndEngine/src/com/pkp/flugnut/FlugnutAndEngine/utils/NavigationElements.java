package com.pkp.flugnut.FlugnutAndEngine.utils;

import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
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
    private BitmapTextureAtlas navigationBitmapTextureAtlas;

    Map<NavigationButtons, Sprite> navigationButtonCache;


    public NavigationElements() {
        navigationButtonCache = new HashMap<NavigationButtons, Sprite>(1);
    }

    public enum NavigationButtons {
        BACK_TO_MAIN_MENU;
    }

    public void initResources(GLGame game) {
        this.navigationBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 129, 226, TextureOptions.BILINEAR);
        this.backButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(navigationBitmapTextureAtlas, game, GameConstants.ASSET_SETTING_BUTTONS, 0, 0);
        this.backButtonTextureRegion = TextureRegionFactory.extractFromTexture(navigationBitmapTextureAtlas, 64, 64, 64, 64);  //Cropping the buttons image to get the back button
        this.navigationBitmapTextureAtlas.load();
    }

    public Sprite getBackToMainMenuButton(final int x, final int y, final GLGame game, final VertexBufferObjectManager objectManager) {
        if (navigationButtonCache.containsKey(NavigationButtons.BACK_TO_MAIN_MENU)) {
            return navigationButtonCache.get(NavigationButtons.BACK_TO_MAIN_MENU);
        }
        initResources(game);
        final Sprite backButton = new Sprite(x, y, backButtonTextureRegion, objectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                game.setNewScene((BaseGameScene) NavigationRedirect.getInstance().getObjectToNavigate(GameConstants.MAIN_MENU_NAV, game));
                return true;
            }
        };

        navigationButtonCache.put(NavigationButtons.BACK_TO_MAIN_MENU, backButton);
        return backButton;
    }
}
