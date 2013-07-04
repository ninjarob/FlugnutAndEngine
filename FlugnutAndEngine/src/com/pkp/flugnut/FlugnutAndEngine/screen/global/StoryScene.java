package com.pkp.flugnut.FlugnutAndEngine.screen.global;

import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
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

public class StoryScene extends BaseGameScene {

    // ===========================================================
    // Fields
    // ===========================================================
    AutoParallaxBackground autoParallaxBackground;
    VertexBufferObjectManager vertexBufferObjectManager;

    private BitmapTextureAtlas mAutoParallaxBackgroundTexture;

    private ITextureRegion mParallaxLayerBack;
    private ITextureRegion mParallaxLayerMid;
    private ITextureRegion mParallaxLayerFront;

    private BitmapTextureAtlas mBitmapTextureAtlas;
    private ITextureRegion buttonsTextureRegion;
    private ITextureRegion backButtonTextureRegion;
    private Font mFont;

    public StoryScene(GLGame game) {
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
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-10.0f, new Sprite(0, game.CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront, vertexBufferObjectManager)));
        setBackground(autoParallaxBackground);

        //FONT
        final Text leftText = new Text(25, 180, this.mFont,
                "Flugnut accidently found a planet which he calls Snostrebla\n" +
                "and he accidently introduced Snostreblaians to hyperspace\n" +
                "technology.  He did this not knowing that the Snostreblaians\n" +
                "would use this technology to try to expand their empire and\n" +
                "destroy all lesser civilizations in the process.  Flugnut also\n" +
                "accidently showed them the way to Flugeria (and to Earth, but\n" +
                "that's another story) which they decided they would make their\n" +
                "first conquest.\n" +
                "The Snostreblaians, after taking their time to learn the\n" +
                "technology, gather an army and plan a rather impressive invasion\n" +
                "made their attack on the cities of Flugeria on multiple fronts.\n" +
                "The Flugerian armies of King Cailm were tasked with taking care\n" +
                "enemy ground forces while Flugnut volunteered to defend the\n" +
                "cities against arial attacks using his cunning, strength and\n" +
                "technology.\n" +
                "Flugnut fights to fix his mistake, for the freedom of his world\n" +
                "and the safety of his fiance, Lindzerious.\n",
                new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
        attachChild(leftText);

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
