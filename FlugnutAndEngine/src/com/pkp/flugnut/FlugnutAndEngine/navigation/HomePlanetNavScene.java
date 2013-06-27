package com.pkp.flugnut.FlugnutAndEngine.navigation;

import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
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
    }
}
