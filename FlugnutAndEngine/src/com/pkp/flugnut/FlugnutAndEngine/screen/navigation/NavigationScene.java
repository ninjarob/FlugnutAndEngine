package com.pkp.flugnut.FlugnutAndEngine.screen.navigation;

import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 7/1/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class NavigationScene extends BaseGameScene {
    protected NavigationScene(GLGame game) {
        super(game);
    }

    public abstract boolean hasCompletedPreviousScene();
}
