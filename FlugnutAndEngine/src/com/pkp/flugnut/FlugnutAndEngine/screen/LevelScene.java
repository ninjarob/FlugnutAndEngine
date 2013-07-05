package com.pkp.flugnut.FlugnutAndEngine.screen;

import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
import com.pkp.flugnut.FlugnutAndEngine.model.level.Level;
import com.pkp.flugnut.FlugnutAndEngine.screen.global.MapScene;
import com.pkp.flugnut.FlugnutAndEngine.screen.global.PauseMenu;
import com.pkp.flugnut.FlugnutAndEngine.screen.global.TutorialScene;
import com.pkp.flugnut.FlugnutAndEngine.utils.GameConstants;
import org.andengine.entity.scene.Scene;
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
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class LevelScene extends BaseGameScene {

    // ===========================================================
    // Fields
    // ===========================================================
    SpriteBackground normalBackground;
    VertexBufferObjectManager vertexBufferObjectManager;

    private BitmapTextureAtlas backgroundTexture;

    private ITextureRegion mapBackground;

    private BitmapTextureAtlas mBitmapTextureAtlas;
    private ITextureRegion levelSelect;

    private ITextureRegion buttonTextureRegion;
    private ITextureRegion spriteTextureRegion;
    private ITextureRegion pauseButtonTextureRegion;
    private Font mFont;

    private Level level;
    private Scene self;

    private PauseMenu pauseMenu;

    public boolean tutorial;

    public LevelScene(GLGame game, Level level, boolean tutorial) {
        super(game);
        this.level = level;
        self = this;
        this.tutorial = tutorial;
    }

    @Override
    public void initResources() {
        //FONT
        FontFactory.setAssetBasePath("font/");
        final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.mFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(), "Droid.ttf", 16, true, android.graphics.Color.WHITE);
        this.mFont.load();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        //BACKGROUND
        this.backgroundTexture = new BitmapTextureAtlas(game.getTextureManager(), 800, 1200);
        this.mapBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backgroundTexture, game, GameConstants.BACKGROUND_IMAGE_FILE, 0, 0);
        this.backgroundTexture.load();

        //BACK BUTTON
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 129, 226, TextureOptions.BILINEAR);
        this.buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "buttons.png", 0, 0);
        this.pauseButtonTextureRegion = TextureRegionFactory.extractFromTexture(mBitmapTextureAtlas, 64, 128, 64, 64);
        this.mBitmapTextureAtlas.load();
    }

    @Override
    public void initScene() {
        //BACKGROUND
        normalBackground = new SpriteBackground(0, 0, 0, new Sprite(0, 0, mapBackground, game.getVertexBufferObjectManager()));
        vertexBufferObjectManager = game.getVertexBufferObjectManager();
        setBackground(normalBackground);

        //BACK BUTTON
        final Sprite pauseButton = new Sprite(10, 74, pauseButtonTextureRegion, game.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (!game.isGamePaused()) {
                    pauseMenu = new PauseMenu(game, self);
                    pauseMenu.initResources();
                    pauseMenu.initMenu();
                }
                return true;
            }
        };
        attachChild(pauseButton);
        registerTouchArea(pauseButton);
        setTouchAreaBindingOnActionDownEnabled(true);
    }

    public void back() {
        if (tutorial) {
            game.setNewScene(new TutorialScene(game));
        } else {
            game.setNewScene(new MapScene(game));
        }
    }
}
