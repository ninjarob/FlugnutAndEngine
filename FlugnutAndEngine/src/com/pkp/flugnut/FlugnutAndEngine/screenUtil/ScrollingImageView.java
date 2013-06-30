package com.pkp.flugnut.FlugnutAndEngine.screenUtil;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

/*
 * This class allows showing a clipped area of an image and having the scene scroll across the image in a defined set of time.
 * Use the constructor to create an object, call initResources in your initResources, call initScene in your initScene
 * 
 * Example: This AustinScene class shows the flug_nut_title.png image and scrolls it
		import android.graphics.Point;
		import android.graphics.Rect;
		import com.pkp.flugnut.FlugnutAndEngine.GLGame;
		import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
		import com.pkp.flugnut.FlugnutAndEngine.screenUtil.ScrollingImageView;

		public class AustinScene extends BaseGameScene {

			private ScrollingImageView scrollingImageView;

			public AustinScene(GLGame game) {
				super(game);

				Point imageSize = new Point(736, 391);
				Rect spriteRect = new Rect(10, 10, 500, 401); // rect to show of the image at a time; this gives both the position on the view and the height/width to show
				String imageFileName = "flugnut_title.png"; // 736X391
				float secondsLength = 5.3f; // how long it takes to scroll from left to right of image
				boolean backAndForth = true; // if true then will keep bouncing back and forth. otherwise it will got left to right and then stop

				scrollingImageView = new ScrollingImageView(game, this, imageSize, spriteRect, imageFileName, secondsLength, backAndForth);
			}

			@Override
			public void initResources() {
				scrollingImageView.initResources();
			}

			@Override
			public void initScene() {
				scrollingImageView.initScene();
			}
		}
 */
public class ScrollingImageView {

	// == parameters from the caller ==
	private Point imageSize; // the size of the actual image
	private Rect spriteRect; // rect to show of the image at a time; this gives both the position on the view and the height/width to show
	private String imageFileName;
	private float secondsLength; // how long it takes to scroll from left to right of image
	private boolean backAndForth; // if true then will keep bouncing back and forth. otherwise it will got left to right and then stop
	private GLGame game;
	private Scene scene;
	// == values used internally ==
	private static final float optimizationFactor = 5.0f; // instead of 1 pixel per move, move this factor of pixels (moving just one pixel per time was too processor intensive
	private BitmapTextureAtlas imageBitmapTextureAtlas;
	private Sprite imageSprite;
	private RectF imageRect; // the rect of the full image
	private RectF spriteSliderRect; // the current rectangle partial view of the entire image
	private PointF imageStep;
	private int moveModifier = 1; // -1 moves to the left
	private PointF margin;// this is how many pixels it takes to move from left/top to right/bottom of the image in the view port
	private float secondsPerPixel; // how many seconds to wait to move another pixel
	private IUpdateHandler timerUpdateHandler;

	/*
	 * imageSize : the full size of the whole image in pixels
	 * spriteRect : the rectangle for the image being shown on the scene; this is smaller than imageSize and is the clipped region shown to the user
	 * imageFileName : the filename of the image to be found in the /gfx folder
	 * secondsLength : how many seconds it takes to travel from left to right showing the image
	 * backAndForth : if true, the view of the image bounces back and forth leftToRight to rightToLeft
	 */
	public ScrollingImageView(final GLGame game, final Scene scene, final Point imageSize, final Rect spriteRect, final String imageFileName, final float secondsLength, final boolean backAndForth) {
		this.scene = scene;
		this.game = game;
		this.imageSize = imageSize;
		this.spriteRect = spriteRect;
		this.imageFileName = imageFileName;
		this.secondsLength = secondsLength;
		this.backAndForth = backAndForth;
	}

	// call this method in your scene's initResources
	// this loads the image and does the calculations
	public void initResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		imageBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), imageSize.x, imageSize.y, TextureOptions.BILINEAR);
		TextureRegion imageBitMapTextureRegiion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(imageBitmapTextureAtlas, game, imageFileName, 0, 0); // 0, 0 is the position of the image
		imageBitmapTextureAtlas.load();

		// get the size of the full image
		imageRect = new RectF(0, 0, imageBitMapTextureRegiion.getWidth(), imageBitMapTextureRegiion.getHeight()); // always put image at top left
		assert (imageRect.width() == imageSize.x && imageRect.height() == imageSize.y); // sanity check

		// see how much space is left besides the view port and divide that in to steps
		margin = new PointF(imageSize.x - spriteRect.width(), imageRect.height() - spriteRect.height());
		secondsPerPixel = (secondsLength / margin.x) * optimizationFactor;
		imageStep = new PointF(1.0f * optimizationFactor, (margin.y / secondsLength) * secondsPerPixel * optimizationFactor); // always move 1 pixel in the x direction; do a ratio move in the y direction so that it concludes at the same time as the x direction

		// set the current sprite to null because there is nothing being shown
		imageSprite = null;

		// set the current sprite view region
		spriteSliderRect = new RectF(0, 0, imageStep.x + spriteRect.width(), imageStep.y + spriteRect.height()); // image is always at top left

		// show the current sprite
		showSprite();
	}

	// call this method in your scene's initScene
	// this starts the timer to scroll the image
	public void initScene() {
		timerUpdateHandler = new TimerHandler(secondsPerPixel, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				moveSprite();
				showSprite();
			}
		});
		scene.registerUpdateHandler(timerUpdateHandler);
	}

	private void moveSprite() {
		spriteSliderRect.left += imageStep.x * moveModifier;
		spriteSliderRect.right += imageStep.x * moveModifier;
		spriteSliderRect.top += imageStep.y * moveModifier;
		spriteSliderRect.bottom += imageStep.y * moveModifier;

		if (spriteSliderRect.left >= imageSize.x - spriteRect.width() || spriteSliderRect.left <= 0) {
			if (backAndForth) {
				moveModifier *= -1; // next move will go in the opposite direction
			} else {
				scene.unregisterUpdateHandler(timerUpdateHandler);
			}
		}
	}

	private void showSprite() {
		Sprite tempSprite;

		// create the new sprite to show
		ITextureRegion clippedRegion = TextureRegionFactory.extractFromTexture(imageBitmapTextureAtlas, (int) spriteSliderRect.left, (int) spriteSliderRect.top, (int) spriteSliderRect.width(), (int) spriteSliderRect.height());
		tempSprite = new Sprite(spriteRect.left, spriteRect.top, clippedRegion, game.getVertexBufferObjectManager());
		// show the new sprite
		scene.attachChild(tempSprite);

		// remove the previous sprite
		if (imageSprite != null) {
			scene.detachChild(imageSprite);
		}

		// remember the new sprite
		imageSprite = tempSprite;
	}
}
