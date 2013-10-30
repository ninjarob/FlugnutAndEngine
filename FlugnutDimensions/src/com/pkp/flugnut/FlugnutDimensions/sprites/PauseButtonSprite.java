package com.pkp.flugnut.FlugnutDimensions.sprites;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.screen.global.PauseMenu;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/29/13
 * Time: 12:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class PauseButtonSprite extends Sprite {
    private GLGame game;
    private PauseMenu pauseMenu;
    private Scene scene;

    public PauseButtonSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager,
                             GLGame game, PauseMenu pauseMenu, Scene scene) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.game = game;
        this.pauseMenu = pauseMenu;
        this.scene = scene;
    }

    @Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        if (!game.isGamePaused()) {
            pauseMenu = new PauseMenu(game, scene);
            pauseMenu.initResources();
            pauseMenu.initMenu();
        }
        return true;
    }
}
