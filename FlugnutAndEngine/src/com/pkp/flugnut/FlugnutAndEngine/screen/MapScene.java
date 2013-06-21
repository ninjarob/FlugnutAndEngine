package com.pkp.flugnut.FlugnutAndEngine.screen;

import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
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

public class MapScene extends BaseGameScene {

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
    private ITextureRegion backButtonTextureRegion;
    private Font mFont;

    public MapScene(GLGame game) {
        super(game);
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
        this.mapBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backgroundTexture, game, "map.png", 0, 0);
        this.backgroundTexture.load();

        //BACK BUTTON
        //this.mBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 129, 193, TextureOptions.BILINEAR);
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 129, 226, TextureOptions.BILINEAR);
        this.buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "buttons.png", 0, 0);
        this.spriteTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "LevelSelect.png", 0, 193);
        this.backButtonTextureRegion = TextureRegionFactory.extractFromTexture(mBitmapTextureAtlas, 64, 64, 64, 64);
        this.mBitmapTextureAtlas.load();
    }

    @Override
    public void initScene() {
        //BACKGROUND
        normalBackground = new SpriteBackground(0, 0, 0, new Sprite(0, 0, mapBackground, game.getVertexBufferObjectManager()));
        vertexBufferObjectManager = game.getVertexBufferObjectManager();
        setBackground(normalBackground);

        initLevels();

        //BACK BUTTON
        final Sprite backButton = new Sprite(10, GLGame.CAMERA_HEIGHT-74, backButtonTextureRegion, game.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                game.setNewScene(new MainMenuScene(game));
                return true;
            }
        };
        attachChild(backButton);
        registerTouchArea(backButton);
        setTouchAreaBindingOnActionDownEnabled(true);
    }

    private void initLevels() {
        //FONT
//        final Text leftText = new Text(50, 180, this.mFont,
//                "Flugnut is the hero of Flugeria.  Herein I will show you\n" +
//                        "how to use flugnut to protect buildings and people, solve\n" +
//                        " puzzles stop the Snostreblaian attack!",
//                new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
//        attachChild(leftText);
        //BACK BUTTON
        final Sprite level1 = new Sprite(185, 7, spriteTextureRegion, game.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                //game.setNewScene(new MainMenuScene(game));
                return true;
            }
        };
        attachChild(level1);
        registerTouchArea(level1);
        setTouchAreaBindingOnActionDownEnabled(true);
    }
}
