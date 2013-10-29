package com.pkp.flugnut.FlugnutDimensions.screen.global;

import android.opengl.GLES20;
import com.pkp.flugnut.FlugnutDimensions.game.Settings;
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
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.BaseGameScene;
import org.andengine.util.color.Color;

public class SettingsScene extends BaseGameScene implements MenuScene.IOnMenuItemClickListener {
    protected static final int MENU_SOUNDS = 0;
    protected static final int MENU_MUSIC = MENU_SOUNDS + 1;
    protected static final int MENU_BACK = MENU_MUSIC + 1;
    private final String SOUND_ON = "SOUND ON";
    private final String SOUND_OFF = "SOUND OFF";
    private final String MUSIC_ON = "MUSIC ON";
    private final String MUSIC_OFF = "MUSIC OFF";
    private final String BACK = "GO BACK";


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

    private TextMenuItem soundTextMenuItem;
    private TextMenuItem musicTextMenuItem;
    private TextMenuItem backTextMenuItem;

    public SettingsScene(GLGame game) {
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
    }

    @Override
    public void initScene() {
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

        String soundText = Settings.soundEnabled ? SOUND_ON : SOUND_OFF;
        String musicText = Settings.musicEnabled ? MUSIC_ON : MUSIC_OFF;
        soundTextMenuItem = new TextMenuItem(MENU_SOUNDS, this.mFont, soundText, game.getVertexBufferObjectManager());
        final IMenuItem soundMenuItem = new ColorMenuItemDecorator(soundTextMenuItem, new Color(1,0,0), new Color(1,1,1));
        soundMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(soundMenuItem);

        musicTextMenuItem = new TextMenuItem(MENU_MUSIC, this.mFont, musicText, game.getVertexBufferObjectManager());
        final IMenuItem musicMenuItem = new ColorMenuItemDecorator(musicTextMenuItem, new Color(1,0,0), new Color(1,1,1));
        musicMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(musicMenuItem);

        backTextMenuItem = new TextMenuItem(MENU_BACK, this.mFont, "GO BACK", game.getVertexBufferObjectManager());
        final IMenuItem backMenuItem = new ColorMenuItemDecorator(backTextMenuItem, new Color(1,0,0), new Color(1,1,1));
        backMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(backMenuItem);

        menuScene.buildAnimations();

        //menuScene.setBackgroundEnabled(false);
        menuScene.setBackground(autoParallaxBackground);

        menuScene.setOnMenuItemClickListener(this);
        setChildScene(menuScene, false, true, true);
    }

    @Override
    public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
        switch(pMenuItem.getID()) {
            case MENU_SOUNDS:
                Settings.soundEnabled = !Settings.soundEnabled;
                String soundText = Settings.soundEnabled ? SOUND_ON : SOUND_OFF;
                soundTextMenuItem.setText(soundText);
                return true;
            case MENU_MUSIC:
                Settings.musicEnabled = !Settings.musicEnabled;
                String musicText = Settings.musicEnabled ? MUSIC_ON : MUSIC_OFF;
                musicTextMenuItem.setText(musicText);
                if (Settings.musicEnabled) {
                    game.mMusic.seekTo(0);
                    game.mMusic.play();
                }
                else {
                    game.mMusic.pause();
                }
                return true;
            case MENU_BACK:
                game.setNewScene(new MainMenuScene(game));
                return true;
            default:
                return false;
        }
    }
}
