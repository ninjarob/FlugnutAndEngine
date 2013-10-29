package com.pkp.flugnut.FlugnutDimensions.screenUtil;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.level.Level;
import com.pkp.flugnut.FlugnutDimensions.game.BaseGameScene;
import com.pkp.flugnut.FlugnutDimensions.sprites.LevelTileSprite;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 7/5/13
 * Time: 4:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class LevelTilesGenerator {


    float mInitialX;
    float mInitialY;
    GLGame game;
    BaseGameScene gameScene;

    public LevelTilesGenerator(BaseGameScene gameScene) {

        this.game = gameScene.getGame();
        this.gameScene = gameScene;

        final float halfLevelSelectorWidth = ((GameConstants.TILE_DIMENSION_X * GameConstants.DEFAULT_COLUMNS) + GameConstants.DEFAULT_PADDING * (GameConstants.DEFAULT_COLUMNS - 1)) * 0.5f;
        this.mInitialX = (game.CAMERA_WIDTH * 0.5f) - halfLevelSelectorWidth;

        final float halfLevelSelectorHeight = ((GameConstants.TILE_DIMENSION_Y * GameConstants.DEFAULT_ROWS) + GameConstants.DEFAULT_PADDING * (GameConstants.DEFAULT_ROWS - 1)) * 0.5f;
        this.mInitialY = (game.CAMERA_HEIGHT * 0.5f) + halfLevelSelectorHeight;

    }

    public void generateTiles(List<Level> levelList) {

        int rows = levelList.size() % GameConstants.DEFAULT_ROWS;
        int cols = GameConstants.DEFAULT_COLUMNS;

        float tempX = this.mInitialX + GameConstants.TILE_DIMENSION_X * 0.5f;
        float tempY = this.mInitialY - GameConstants.TILE_DIMENSION_Y * 0.5f;

        int counter = 0;


        for (int i = 0; i < rows; i++) {
            for (int o = 0; o < cols; o++) {
                if (counter < levelList.size()) {
                    Level level = levelList.get(counter);

                    counter++;

                    /* Create a level tile */
                    LevelTileSprite levelTile = new LevelTileSprite(tempX, tempY, level.locked,
                            counter, game.getNavigationElements().getLevelTileResources(game),
                            gameScene.getDefaultFont(), gameScene.getDefaultObjectManager()) {
                        @Override
                        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                            if (pSceneTouchEvent.isActionDown()) {
                                if (mIsLocked) {
                                    game.getCurrentScene().getBackground().setColor(Color.RED);
                                } else {
                                    game.getCurrentScene().getBackground().setColor(Color.GREEN);
                                }
                                return true;

                            }
                            return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                        }
                    };
                    levelTile.attachText();

                    gameScene.registerTouchArea(levelTile);
                    gameScene.attachChild(levelTile);


                    /* Increment the tempX coordinate to the next column */
                    tempX = tempX + GameConstants.TILE_DIMENSION_X + GameConstants.DEFAULT_PADDING;
                }
            }
            /* Reposition the tempX coordinate back to the first row (far left) */
            tempX = mInitialX + GameConstants.TILE_DIMENSION_X * 0.5f;

            /* Reposition the tempY coordinate for the next row to apply tiles */
            tempY = tempY - GameConstants.TILE_DIMENSION_Y - GameConstants.DEFAULT_PADDING;
        }
    }
}
