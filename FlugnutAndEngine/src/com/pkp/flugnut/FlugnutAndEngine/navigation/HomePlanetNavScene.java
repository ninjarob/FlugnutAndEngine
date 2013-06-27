package com.pkp.flugnut.FlugnutAndEngine.navigation;

import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
import com.pkp.flugnut.FlugnutAndEngine.utils.GameConstants;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/25/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomePlanetNavScene extends BaseGameScene {

    public HomePlanetNavScene(GLGame game) {
        super(game);
    }

    @Override
    public void initResources() {

    }

    @Override
    public void initScene() {
        setBackground(defaultBackground);
        final Text leftText = new Text(50, 180, defaultFont,
                "This is the home planet level selection page",
                new TextOptions(HorizontalAlign.LEFT), defaultObjectManager);
        attachChild(leftText);

        final Sprite backButton = game.getNavigationElements().getBackButton(BACK_BUTTON_NAV_X, BACK_BUTTON_NAV_Y, game, GameConstants.HOME_PLANET_BACK_NAV);
        attachChild(backButton);
        registerTouchArea(backButton);

    }
}
