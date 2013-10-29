package com.pkp.flugnut.FlugnutDimensions.sprites;

import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 7/5/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class LevelTileSprite extends Sprite {

    private Font mFont;
    protected boolean mIsLocked;
    private int mLevelNumber;
    private Text mTileText;
    private VertexBufferObjectManager objectManager;

    public LevelTileSprite(float pX, float pY, boolean pIsLocked, int pLevelNumber, ITextureRegion pTextureRegion, Font pFont, VertexBufferObjectManager objectManager) {

        super(pX, pY, GameConstants.TILE_DIMENSION_X, GameConstants.TILE_DIMENSION_Y, pTextureRegion, objectManager);

        this.mFont = pFont;
        this.mIsLocked = !pIsLocked;
        this.mLevelNumber = pLevelNumber;
        this.objectManager = objectManager;
    }


    public void attachText() {
        String tileTextString = GameConstants.EMPTY_STRING;

        if (this.mTileText == null) {
            if (this.mIsLocked) {
                tileTextString = GameConstants.LOCKED;
            } else {
                tileTextString = String.valueOf(this.mLevelNumber);
            }

            // Setup the text position to be placed in the center of the tile
            final float textPositionX = GameConstants.TILE_DIMENSION_X * 0.5f;
            final float textPositionY = textPositionX;


            // Create the tile's text in the center of the tile
            this.mTileText = new Text(textPositionX, textPositionY, this.mFont, tileTextString, tileTextString.length(), objectManager);


            // Attach the Text to the LevelTile
            this.attachChild(mTileText);
        }
    }

}
