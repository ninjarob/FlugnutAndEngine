package com.pkp.flugnut.FlugnutDimensions.screen.global;

import android.app.Dialog;
import android.opengl.GLES20;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.client.SmartFoxBase;
import com.pkp.flugnut.FlugnutDimensions.game.BaseGameScene;
import com.pkp.flugnut.FlugnutDimensions.game.Settings;
import com.pkp.flugnut.FlugnutDimensions.utils.DialogHandler;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import com.pkp.flugnut.FlugnutDimensions.utils.NavigationRedirect;
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

public class MainMenuScene extends BaseGameScene implements MenuScene.IOnMenuItemClickListener {
    protected static final int MENU_PLAY = 0;
    protected static final int MENU_TUTORIAL = MENU_PLAY + 1;
    protected static final int MENU_HELP = MENU_TUTORIAL + 1;
    protected static final int MENU_STORY = MENU_HELP + 1;
    protected static final int MENU_SETTINGS = MENU_STORY + 1;
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

    private SmartFoxBase sfb;

    public MainMenuScene(GLGame game) {
        super(game);
        sfb = new SmartFoxBase(game);
    }

    @Override
    public void initResources() {
        FontFactory.setAssetBasePath(GameConstants.FONT_DIR);
        final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.mFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(), GameConstants.FONT_PLOK, 32, true, android.graphics.Color.WHITE);
        this.mFont.load();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GameConstants.GRAPHICS_DIR);
        this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(game.getTextureManager(), 800, 1200);
        this.mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, game, GameConstants.PARALLAX_BACKGROUND_FRONT_FILE, 0, 0);
        this.mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, game, GameConstants.BACKGROUND_IMAGE_FILE, 0, 188);  //plus the height of the front
        this.mParallaxLayerMid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, game, GameConstants.PARALLAX_BACKGROUND_MID_FILE, 0, 989);   //plus the height of the front and the back
        this.mAutoParallaxBackgroundTexture.load();

        if (game.mMusic == null) {
            MusicFactory.setAssetBasePath(GameConstants.MUSIC_DIR);
            try {
                game.mMusic = MusicFactory.createMusicFromAsset(game.getMusicManager(), game, GameConstants.MUSIC_DEFAULT_THEME);
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
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-10.0f, new Sprite(0, GLGame.CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront, vertexBufferObjectManager)));
        setBackgroundEnabled(false);
        createMenuScene();
    }

    protected void createMenuScene() {
        menuScene = new MenuScene(game.mCamera);

        final IMenuItem playMenuItem = getMenuItem(MENU_PLAY, GameConstants.LABEL_PLAY_BUTTON);
        menuScene.addMenuItem(playMenuItem);

        final IMenuItem tutorialMenuItem = getMenuItem(MENU_TUTORIAL, GameConstants.LABEL_TUTORIAL_BUTTON);
        menuScene.addMenuItem(tutorialMenuItem);

        final IMenuItem helpMenuItem = getMenuItem(MENU_HELP, GameConstants.LABEL_HELP_BUTTON);
        menuScene.addMenuItem(helpMenuItem);

        final IMenuItem storyMenuItem = getMenuItem(MENU_STORY, GameConstants.LABEL_STORY_BUTTON);
        menuScene.addMenuItem(storyMenuItem);

        final IMenuItem settingsMenuItem = getMenuItem(MENU_SETTINGS, GameConstants.LABEL_SETTINGS_BUTTON);
        menuScene.addMenuItem(settingsMenuItem);

        final IMenuItem quitMenuItem = getMenuItem(MENU_QUIT, GameConstants.LABEL_QUIT_BUTTON);
        menuScene.addMenuItem(quitMenuItem);

        menuScene.buildAnimations();

        //menuScene.setBackgroundEnabled(false);
        menuScene.setBackground(autoParallaxBackground);

        menuScene.setOnMenuItemClickListener(this);
        setChildScene(menuScene, false, true, true);
    }

    private IMenuItem getMenuItem(int menuItemPosition, String menuItemLabel) {
        final IMenuItem quitMenuItem = new ColorMenuItemDecorator(
                new TextMenuItem(menuItemPosition, this.mFont, menuItemLabel, game.getVertexBufferObjectManager()),
                new Color(1, 0, 0),
                new Color(1, 1, 1)
        );
        quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        return quitMenuItem;
    }

    @Override
    public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case MENU_PLAY:
                game.setNewScene(new GameLoadingScene(game, sfb));
                return true;
            case MENU_TUTORIAL:
                game.setNewScene(new TutorialSelectionScene(game, sfb));
                return true;
            case MENU_HELP:
                game.setNewScene(getScene(GameConstants.HELP_MENU_NAV));
                return true;
            case MENU_STORY:
                game.setNewScene(getScene(GameConstants.STORY_SCENE_NAV));
                return true;
            case MENU_SETTINGS:
                game.setNewScene(getScene(GameConstants.SETTINGS_MENU_NAV));
                return true;
            case MENU_QUIT:
                sfb.disconnect();
                game.finish();
                return true;
            default:
                return false;
        }
    }

    private BaseGameScene getScene(String redirectId) {
        return (BaseGameScene) NavigationRedirect.getInstance().getObjectToNavigate(redirectId, game);
    }
}
