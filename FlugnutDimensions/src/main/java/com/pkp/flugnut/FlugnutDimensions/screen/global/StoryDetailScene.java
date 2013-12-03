package com.pkp.flugnut.FlugnutDimensions.screen.global;

import android.graphics.Point;
import android.graphics.Rect;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.BaseGameScene;
import com.pkp.flugnut.FlugnutDimensions.screenUtil.ScrollingImageView;
import org.andengine.entity.scene.background.Background;
import org.andengine.util.color.Color;

public class StoryDetailScene extends BaseGameScene {

	private ScrollingImageView scrollingImageView;

	public void setImageFileName(String imageFileName, int width, int height) {
		Point imageSize = new Point(width, height);
//		Rect spriteRect = new Rect(10, 10, 500, 401); // rect to show of the image at a time; this gives both the position on the view and the height/width to show
		Rect spriteRect = new Rect(10, 10, 500, 281); // rect to show of the image at a time; this gives both the position on the view and the height/width to show
		float secondsLength = 10.0f; // how long it takes to scroll from left to right of image
		boolean backAndForth = false; // if true then will keep bouncing back and forth. otherwise it will got left to right and then stop

		scrollingImageView = new ScrollingImageView(game, this, imageSize, spriteRect, imageFileName, secondsLength, backAndForth);
	}

	public StoryDetailScene(GLGame game) {
		super(game);
	}

	@Override
	public void initResources() {
		scrollingImageView.initResources();
	}

	@Override
	public void initScene() {
		scrollingImageView.initScene();

		this.setBackground(new Background(Color.WHITE));
	}
}
