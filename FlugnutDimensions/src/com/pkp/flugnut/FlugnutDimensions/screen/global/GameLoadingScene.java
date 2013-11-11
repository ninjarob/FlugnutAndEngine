package com.pkp.flugnut.FlugnutDimensions.screen.global;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.client.SmartFoxBase;
import com.pkp.flugnut.FlugnutDimensions.game.BaseGameScene;
import com.pkp.flugnut.FlugnutDimensions.game.ConnectionStatus;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
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
import org.andengine.util.HorizontalAlign;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameLoadingScene extends BaseGameScene implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        ConnectionStatus s = (ConnectionStatus)event.getSource();
        statusText.setText("Status:" + event.getNewValue());
        switch(s.getStatus()) {
            case CONNECTED:
                sfb.login();
                return;
            case LOGGED:
                sfb.joinRoom(systemToLoad);
                return;
            case IN_A_ROOM:
                sfb.sendReadyGame();
                return;
            case CONNECTION_ERROR:
            case CONNECTION_LOST:
            case DISCONNECTED:
            case CONNECTING:
                return;
        }
    }

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
    private Text statusText;
    private BitmapTextureAtlas mBitmapTextureAtlas;
    private ITextureRegion buttonsTextureRegion;
    private ITextureRegion backButtonTextureRegion;
    private SmartFoxBase sfb;

    private String systemToLoad = "SolSystem";
    //private String userToLoginWith = "Joseph";

    public GameLoadingScene(GLGame game, SmartFoxBase sfb) {
        super(game);
        this.sfb = sfb;
        sfb.getStatus().addChangeListener(this);
        sfb.connect();
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
        this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(game.getTextureManager(), 800, 1200);
        this.mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, game, "parallax_background_layer_front.png", 0, 0);
        this.mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, game, "spacebg1-half-noalpha-smaller.gif", 0, 188);  //plus the height of the front
        this.mParallaxLayerMid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, game, "parallax_background_layer_mid.png", 0, 989);   //plus the height of the front and the back
        this.mAutoParallaxBackgroundTexture.load();

        //BACK BUTTON
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 129, 193, TextureOptions.BILINEAR);
        this.buttonsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "buttons.png", 0, 0);
        this.backButtonTextureRegion = TextureRegionFactory.extractFromTexture(mBitmapTextureAtlas, 64, 64, 64, 64);
        this.mBitmapTextureAtlas.load();
    }

    @Override
    public void initScene() {
        //BACKGROUND
        autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
        vertexBufferObjectManager = game.getVertexBufferObjectManager();
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0.0f, new Sprite(0, 0, this.mParallaxLayerBack, vertexBufferObjectManager)));
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-5.0f, new Sprite(0, 80, this.mParallaxLayerMid, vertexBufferObjectManager)));
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-10.0f, new Sprite(0, GLGame.CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront, vertexBufferObjectManager)));
        setBackground(autoParallaxBackground);

        //FONT
        statusText = new Text(50, 180, this.mFont,
                "Status: " + sfb.getStatus(),
                new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
        attachChild(statusText);

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
}
