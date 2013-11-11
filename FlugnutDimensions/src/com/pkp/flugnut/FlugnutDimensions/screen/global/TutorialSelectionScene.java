package com.pkp.flugnut.FlugnutDimensions.screen.global;

import android.opengl.GLES20;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.client.SmartFoxBase;
import com.pkp.flugnut.FlugnutDimensions.game.BaseGameScene;
import com.pkp.flugnut.FlugnutDimensions.game.ConnectionStatus;
import com.pkp.flugnut.FlugnutDimensions.game.Settings;
import com.pkp.flugnut.FlugnutDimensions.utils.Utilities;
import org.andengine.audio.music.MusicFactory;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import java.io.IOException;

public class TutorialSelectionScene extends BaseGameScene implements MenuScene.IOnMenuItemClickListener {
    protected static final int TUT_ONE = 0;
    protected static final int TUT_TWO = TUT_ONE+ 1;
    protected static final int TUT_THREE = TUT_TWO + 1;
    protected static final int BACK = TUT_THREE + 1;

    // ===========================================================
    // Fields
    // ===========================================================
    AutoParallaxBackground autoParallaxBackground;
    VertexBufferObjectManager vertexBufferObjectManager;

    private BitmapTextureAtlas mAutoParallaxBackgroundTexture;

    private ITextureRegion mParallaxLayerBack;
    private ITextureRegion mParallaxLayerMid;
    private ITextureRegion mParallaxLayerFront;

    private Font mFont;
    private MenuScene menuScene;

    private BitmapTextureAtlas mBitmapTextureAtlas;
    private ITextureRegion buttonsTextureRegion;
    private ITextureRegion backButtonTextureRegion;
    private SmartFoxBase sfb;

	public TutorialSelectionScene(GLGame game, SmartFoxBase sfb) {
		super(game);
        this.sfb = sfb;
	}

    @Override
    public void initResources() {
        FontFactory.setAssetBasePath("font/");
        final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.mFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(), "Plok.ttf", 24, true, android.graphics.Color.WHITE);
        this.mFont.load();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(game.getTextureManager(), 800, 1200);
        this.mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, game, "parallax_background_layer_front.png", 0, 0);
        this.mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, game, "spacebg1-half-noalpha-smaller.gif", 0, 188);  //plus the height of the front
        this.mParallaxLayerMid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, game, "parallax_background_layer_mid.png", 0, 989);   //plus the height of the front and the back
        this.mAutoParallaxBackgroundTexture.load();

        if (game.mMusic == null) {
            MusicFactory.setAssetBasePath("mfx/");
            try {
                game.mMusic = MusicFactory.createMusicFromAsset(game.getMusicManager(), game, "flugnutmaintheme.mp3");
                game.mMusic.setLooping(true);
                game.mMusic.setVolume(Settings.musicVolume);
            } catch (final IOException e) {
                Debug.e(e);
            }
        }
    }

    @Override
    public void initScene() {
        Utilities.playMusic(game.mMusic);
        autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
        vertexBufferObjectManager = game.getVertexBufferObjectManager();
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0.0f, new Sprite(0, 0, this.mParallaxLayerBack, vertexBufferObjectManager)));
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-5.0f, new Sprite(0, 80, this.mParallaxLayerMid, vertexBufferObjectManager)));
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-10.0f, new Sprite(0, game.CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront, vertexBufferObjectManager)));
        setBackgroundEnabled(false);
        createMenuScene();
    }

    protected void createMenuScene() {
        menuScene = new MenuScene(game.mCamera);

        final IMenuItem tut1MenuItem = new ColorMenuItemDecorator(new TextMenuItem(TUT_ONE, this.mFont, "Collect the Birds", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        tut1MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(tut1MenuItem);

        final IMenuItem tut2MenuItem = new ColorMenuItemDecorator(new TextMenuItem(TUT_TWO, this.mFont, "Fix the Cell Tower", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        tut2MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(tut2MenuItem);

        final IMenuItem tut3MenuItem = new ColorMenuItemDecorator(new TextMenuItem(TUT_THREE, this.mFont, "Power Balloons", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        tut3MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(tut3MenuItem);

        final IMenuItem backItem = new ColorMenuItemDecorator(new TextMenuItem(BACK, this.mFont, "back", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        backItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(backItem);

        menuScene.buildAnimations();

        menuScene.setBackground(autoParallaxBackground);

        menuScene.setOnMenuItemClickListener(this);
        setChildScene(menuScene, false, true, true);
    }

    @Override
    public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
        switch(pMenuItem.getID()) {
            case TUT_ONE:
                return true;
            case TUT_TWO:
                return true;
            case TUT_THREE:
                return true;
            case BACK:
                game.setNewScene(new MainMenuScene(game));
                return true;
            default:
                return false;
        }
    }
}
