package com.pkp.flugnut.FlugnutAndEngine.screen;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

public class AustinScene extends BaseGameScene {

	private BitmapTextureAtlas imageBitmapTextureAtlas;
	private Sprite imageSprite;
	private RectF imageRect; // the rect of the full image
	private RectF spriteSliderRect; // the current rectangle partial view of the entire image
	private PointF imageStep;
	private int moveModifier = 1; // -1 moves to the left
	private int currentFrame = 0;
	// parameters
	private final Point imageSize = new Point(736, 391);
	private final Rect spriteRect = new Rect(10, 10, 200, 401); // rect to show of the image at a time; this gives both the position on the view and the height/width to show
	private final int numberFrames = 20;
	private final String imageFileName = "flugnut_title.png"; // 736X391

	public AustinScene(GLGame game) {
		super(game);
	}

	@Override
	public void initResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		imageBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), imageSize.x, imageSize.y, TextureOptions.BILINEAR);
		TextureRegion imageBitMapTextureRegiion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(imageBitmapTextureAtlas, game, imageFileName, 0, 0); // 0, 0 is the position of the image
		imageBitmapTextureAtlas.load();

		// get the size of the full image
		imageRect = new RectF(0, 0, imageBitMapTextureRegiion.getWidth(), imageBitMapTextureRegiion.getHeight()); // always put image at top left
		assert (imageRect.width() == imageSize.x && imageRect.height() == imageSize.y); // sanity check

		// see how much space is left besides the view port and divide that in to steps
		imageStep = new PointF((imageSize.x - spriteRect.width()) / numberFrames, (imageRect.height() - spriteRect.height()) / numberFrames);

		// set the current sprite to null because there is nothing being shown
		imageSprite = null;

		// set the current sprite view
		spriteSliderRect = new RectF(0, 0, imageStep.x + spriteRect.width(), imageStep.y + spriteRect.height()); // image is always at top left

		// show the current sprite
		showSprite();
	}

	@Override
	public void initScene() {
		registerUpdateHandler(new TimerHandler(1.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				moveSprite();
				showSprite();
			}
		}));
	}

	private void moveSprite() {
		currentFrame += moveModifier; // increment / decrement frame
		if (currentFrame >= numberFrames) { // switch modifier so it bounces back and forth
			currentFrame = numberFrames - 1;
			moveModifier *= -1;
		} else if (currentFrame < 0) {
			currentFrame = 0;
			moveModifier *= -1;
		}

		spriteSliderRect.left += imageStep.x * moveModifier;
		spriteSliderRect.right += imageStep.x * moveModifier;
		spriteSliderRect.top += imageStep.y * moveModifier;
		spriteSliderRect.bottom += imageStep.y * moveModifier;

	}

	private void showSprite() {
		Sprite tempSprite;

		// create the new sprite to show
		ITextureRegion clippedRegion = TextureRegionFactory.extractFromTexture(imageBitmapTextureAtlas, (int) spriteSliderRect.left, (int) spriteSliderRect.top, (int) spriteSliderRect.width(), (int) spriteSliderRect.height());
		tempSprite = new Sprite(spriteRect.left, spriteRect.top, clippedRegion, game.getVertexBufferObjectManager());
		attachChild(tempSprite);

		if (imageSprite != null) {
			detachChild(imageSprite);
		}
		imageSprite = tempSprite;
	}
	// combine clippedRegion replaces spritesliderrect
//	HE IS ALREADY SCROLLING ON THE MAIN PAGE!!!
}
