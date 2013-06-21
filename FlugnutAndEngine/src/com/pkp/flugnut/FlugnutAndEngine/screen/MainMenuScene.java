package com.pkp.flugnut.FlugnutAndEngine.screen;

import android.opengl.GLES20;
import com.pkp.flugnut.FlugnutAndEngine.game.Settings;
import com.pkp.flugnut.FlugnutAndEngine.utils.Utilities;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
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
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
import com.pkp.flugnut.FlugnutAndEngine.game.Swipeable;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import java.io.IOException;

public class MainMenuScene extends BaseGameScene implements MenuScene.IOnMenuItemClickListener {
    protected static final int MENU_PLAY = 0;
    protected static final int MENU_TUTORIAL = MENU_PLAY + 1;
    protected static final int MENU_HELP = MENU_TUTORIAL + 1;
    protected static final int MENU_STORY = MENU_HELP + 1;
    protected static final int MENU_SETTINGS = MENU_STORY+ 1;
    protected static final int MENU_QUIT = MENU_SETTINGS + 1;


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

	public MainMenuScene(GLGame game) {
		super(game);
	}

    @Override
    public void initResources() {
        FontFactory.setAssetBasePath("font/");
        final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.mFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(), "Plok.ttf", 48, true, android.graphics.Color.WHITE);
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

        final IMenuItem playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_PLAY, this.mFont, "PLAY", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        playMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(playMenuItem);

        final IMenuItem tutorialMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_TUTORIAL, this.mFont, "TUTORIAL", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        tutorialMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(tutorialMenuItem);

        final IMenuItem helpMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_HELP, this.mFont, "HELP", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        helpMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(helpMenuItem);

        final IMenuItem storyMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_STORY, this.mFont, "STORY", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        storyMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(storyMenuItem);

        final IMenuItem settingsMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_SETTINGS, this.mFont, "SETTINGS", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        settingsMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(settingsMenuItem);

        final IMenuItem quitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_QUIT, this.mFont, "QUIT", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(quitMenuItem);

        menuScene.buildAnimations();

        //menuScene.setBackgroundEnabled(false);
        menuScene.setBackground(autoParallaxBackground);

        menuScene.setOnMenuItemClickListener(this);
        setChildScene(menuScene, false, true, true);
    }

    @Override
    public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
        switch(pMenuItem.getID()) {
            case MENU_PLAY:
                game.setNewScene(new MapScene(game));
                return true;
            case MENU_TUTORIAL:
                game.setNewScene(new TutorialScene(game));
                return true;
            case MENU_HELP:
                game.setNewScene(new HelpScene(game));
                return true;
            case MENU_STORY:
                game.setNewScene(new StoryScene(game));
                return true;
            case MENU_SETTINGS:
                game.setNewScene(new SettingsScene(game));
                return true;
            case MENU_QUIT:
                game.finish();
                return true;
            default:
                return false;
        }
    }
}
