package com.pkp.flugnut.FlugnutDimensions.game;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.math.BigDecimal;

public abstract class BaseGameScene extends Scene {
    public static float SSC; //SCREEN_SIZE_CONSTANT
    protected double timePassed = 0;
    protected boolean transitionIn;
    protected boolean transitionOut;
    protected GLGame game;
    protected static Font defaultFont;
    protected static VertexBufferObjectManager defaultObjectManager;
    protected static SpriteBackground defaultBackground;

    protected final int BACK_BUTTON_NAV_X = new BigDecimal(GLGame.CAMERA_WIDTH - (GLGame.CAMERA_WIDTH * 0.10)).intValue();
    protected final int BACK_BUTTON_NAV_Y = new BigDecimal(GLGame.CAMERA_HEIGHT - (GLGame.CAMERA_WIDTH * 0.10)).intValue();

    public enum ScreenType {
        MainMenu,
        Game,
        Help,
        Story,
        Map
    }

    protected ScreenType nextScreen;


    public BaseGameScene(GLGame game) {
        //SSC = ((float)(height+width))/853f;
        this.game = game;
        transitionIn = true;
        transitionOut = false;
        getDefaultFont();
        getDefaultObjectManager();
        getDefaultBackground();
    }

    public abstract void initResources();

    public abstract void initScene();

    public void update(float deltaTime) {
        if (transitionIn) {
            //transitionIn = transitionIn(deltaTime);
        } else if (transitionOut) {
            //transitionOut = transitionOut(deltaTime);
            if (!transitionOut) {
                if (nextScreen != null) {
                    //Utilities.setTheNextScreen((GLGame) game, nextScreen);
                } else {
                    System.out.println("here");
                }
            }
        }
    }

    public Font getDefaultFont() {
        if (defaultFont != null) {
            return defaultFont;
        }

        FontFactory.setAssetBasePath(GameConstants.FONT_DIR);
        final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.defaultFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(),
                GameConstants.FONT_DROID, 16, true, android.graphics.Color.WHITE);
        this.defaultFont.load();
        return defaultFont;
    }


    public VertexBufferObjectManager getDefaultObjectManager() {
        if (defaultObjectManager != null) {
            return defaultObjectManager;
        }
        defaultObjectManager = game.getVertexBufferObjectManager();
        return defaultObjectManager;
    }

    public SpriteBackground getDefaultBackground() {
        if (defaultBackground != null) {
            return defaultBackground;
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GameConstants.GRAPHICS_DIR);
        BitmapTextureAtlas backgroundBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 800, 1200);
        ITextureRegion textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundBitmapTextureAtlas, game, GameConstants.BACKGROUND_IMAGE_FILE, 0, 188);
        backgroundBitmapTextureAtlas.load();

        defaultBackground = new SpriteBackground(0, 0, 0, new Sprite(0, 0, textureRegion, defaultObjectManager));
        return defaultBackground;
    }

    public GLGame getGame() {
        return game;
    }
}
