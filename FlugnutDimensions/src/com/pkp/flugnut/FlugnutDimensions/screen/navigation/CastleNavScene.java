package com.pkp.flugnut.FlugnutDimensions.screen.navigation;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.level.Level;
import com.pkp.flugnut.FlugnutDimensions.screenUtil.LevelTilesGenerator;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import com.pkp.flugnut.FlugnutDimensions.utils.LevelXmlParser;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/25/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class CastleNavScene extends NavigationScene {


    public CastleNavScene(GLGame game) {
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

        final Sprite backButton = game.getNavigationElements().getBackButton(BACK_BUTTON_NAV_X, BACK_BUTTON_NAV_Y, game, GameConstants.CASTLE_PLANET_BACK_NAV);
        attachChild(backButton);
        registerTouchArea(backButton);

        buildLevels();
    }

    @Override
    public boolean hasCompletedPreviousScene() {
        return true;
    }

    @Override
    public void buildLevels() {
        List<Level> levelList = LevelXmlParser.getInstance().getLevels(GameConstants.CASTLE_NAV);
        LevelTilesGenerator tilesGenerator = new LevelTilesGenerator(this);
        tilesGenerator.generateTiles(levelList);
    }
}
