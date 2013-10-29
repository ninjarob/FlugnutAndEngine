package com.pkp.flugnut.FlugnutDimensions.screen.global;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import java.util.ArrayList;
import java.util.List;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
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

public class StoryScene extends MenuSceneBase {

	class StoryData {

		private String imageFileName;
		private int height;
		private int width;

		StoryData(String imageFileName, int width, int height) {
			this.imageFileName = imageFileName;
			this.height = height;
			this.width = width;
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
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion buttonsTextureRegion;
	private ITextureRegion backButtonTextureRegion;
	private Font mFont;
	private List<StoryData> levelStoryImages;

	public StoryScene(GLGame game) {
		super(game);
		levelStoryImages = new ArrayList<StoryData>();
		levelStoryImages.add(new StoryData("frank_earnest.png", 900, 271));
		for (int i = 0; i < 8; i++) {
			levelStoryImages.add(new StoryData("flugnut_title.png", 736, 391));
		}
	}

	@Override
	public void initResources() {
		//FONT
		FontFactory.setAssetBasePath("font/");
		final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		mFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(), "Droid.ttf", 16, true, android.graphics.Color.WHITE);
		mFont.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//BACKGROUND
		mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(game.getTextureManager(), 800, 1200);
		mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, game, "parallax_background_layer_front.png", 0, 0);
		mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, game, "spacebg1-half-noalpha-smaller.gif", 0, 188);  //plus the height of the front
		mParallaxLayerMid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, game, "parallax_background_layer_mid.png", 0, 989);   //plus the height of the front and the back
		mAutoParallaxBackgroundTexture.load();

		mBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 129, 193, TextureOptions.BILINEAR);
		buttonsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "buttons.png", 0, 0);
		backButtonTextureRegion = TextureRegionFactory.extractFromTexture(mBitmapTextureAtlas, 64, 64, 64, 64);
		mBitmapTextureAtlas.load();
	}

	@Override
	public void initScene() {
		FontFactory.setAssetBasePath(GameConstants.FONT_DIR);
		final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		mFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(), GameConstants.FONT_PLOK, 48, true, android.graphics.Color.WHITE);
		mFont.load();

		//BACKGROUND
		autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		vertexBufferObjectManager = game.getVertexBufferObjectManager();
		autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0.0f, new Sprite(0, 0, mParallaxLayerBack, vertexBufferObjectManager)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-5.0f, new Sprite(0, 80, mParallaxLayerMid, vertexBufferObjectManager)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-10.0f, new Sprite(0, game.CAMERA_HEIGHT - mParallaxLayerFront.getHeight(), mParallaxLayerFront, vertexBufferObjectManager)));
		setBackground(autoParallaxBackground);

		MenuScene menuScene;
		menuScene = new MenuScene(game.mCamera);
		menuScene.setOnMenuItemClickListener(this);
		setChildScene(menuScene, false, true, true);

		// level buttons
		for (int i = 0; i < levelStoryImages.size(); i++) {
			menuScene.addMenuItem(getMenuItem(i, "Level " + (i + 1), mFont));
		}

		menuScene.buildAnimations();
		menuScene.setBackground(autoParallaxBackground);

		//BACK BUTTON
		final Sprite backButton = new Sprite(10, GLGame.CAMERA_HEIGHT - 74, backButtonTextureRegion, game.getVertexBufferObjectManager()) {
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

	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		StoryDetailScene storyDetailScene = (StoryDetailScene) getScene(GameConstants.STORY_SCENE_DETAIL_NAV);

		StoryData storyData = levelStoryImages.get(pMenuItem.getID());
		storyDetailScene.setImageFileName(storyData.imageFileName, storyData.width, storyData.height);

		game.setNewScene(storyDetailScene);

		return true;
	}
}
