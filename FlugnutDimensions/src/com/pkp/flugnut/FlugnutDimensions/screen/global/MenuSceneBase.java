package com.pkp.flugnut.FlugnutDimensions.screen.global;

import android.opengl.GLES20;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.BaseGameScene;
import com.pkp.flugnut.FlugnutDimensions.utils.NavigationRedirect;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.opengl.font.Font;
import org.andengine.util.color.Color;

public abstract class MenuSceneBase extends BaseGameScene implements MenuScene.IOnMenuItemClickListener {

	public MenuSceneBase(GLGame glGame) {
		super(glGame);
	}

	protected IMenuItem getMenuItem(int menuItemPosition, String menuItemLabel, Font font) {
		final IMenuItem menuItem = new ColorMenuItemDecorator(
				new TextMenuItem(menuItemPosition, font, menuItemLabel, game.getVertexBufferObjectManager()),
				new Color(1, 0, 0),
				new Color(1, 1, 1));

		menuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		return menuItem;
	}

	protected BaseGameScene getScene(String redirectId) {
		return (BaseGameScene) NavigationRedirect.getInstance().getObjectToNavigate(redirectId, game);
	}
}
