package com.pkp.flugnut.FlugnutDimensions.screen.global;

import android.opengl.GLES20;
import android.view.MotionEvent;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.animator.IMenuAnimator;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import java.util.ArrayList;

public class PauseMenu implements IOnAreaTouchListener{
    protected static final int RESUME    = 0;
    protected static final int EXIT = RESUME + 1;


    protected final ArrayList<IMenuItem> mMenuItems = new ArrayList<IMenuItem>();

    private IMenuAnimator mMenuAnimator = IMenuAnimator.DEFAULT;

    private IMenuItem mSelectedMenuItem;

    // ===========================================================
    // Fields
    // ===========================================================
    VertexBufferObjectManager vertexBufferObjectManager;

    private Font mFont;
    private HUD hud;
    private Scene parent;
    private GLGame game;

    private IMenuItem resumeMenuItem;
    private IMenuItem exitMenuItem;

	public PauseMenu(GLGame game, HUD hud, Scene parent) {
        this.game = game;
        this.hud = hud;
        this.parent = parent;
	}

    public void initResources() {
        FontFactory.setAssetBasePath("font/");
        final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.mFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(), "Plok.ttf", 24, true, android.graphics.Color.WHITE);
        this.mFont.load();
    }

    public void initMenu() {
        vertexBufferObjectManager = game.getVertexBufferObjectManager();

        resumeMenuItem = new ColorMenuItemDecorator(new TextMenuItem(RESUME, this.mFont, "Resume", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        resumeMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        addMenuItem(resumeMenuItem);

        exitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(EXIT, this.mFont, "Exit", game.getVertexBufferObjectManager()), new Color(1,0,0), new Color(1,1,1));
        exitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        addMenuItem(exitMenuItem);

        buildAnimations();
        hud.setOnAreaTouchListener(this);
    }

    private void addMenuItem(final IMenuItem pMenuItem) {
        this.mMenuItems.add(pMenuItem);
        hud.attachChild(pMenuItem);
        hud.registerTouchArea(pMenuItem);
    }

//    private void clearMenuItems() {
//        for (int i = this.mMenuItems.size() - 1; i >= 0; i--) {
//            final IMenuItem menuItem = this.mMenuItems.remove(i);
//            parent.detachChild(menuItem);
//            parent.unregisterTouchArea(menuItem);
//        }
//    }

    private void buildAnimations() {
        this.prepareAnimations();

        final float cameraWidthRaw = game.mCamera.getWidthRaw();
        final float cameraHeightRaw = game.mCamera.getHeightRaw();
        this.mMenuAnimator.buildAnimations(this.mMenuItems, cameraWidthRaw, cameraHeightRaw);
    }

    private void prepareAnimations() {
        final float cameraWidthRaw = game.mCamera.getWidthRaw();
        final float cameraHeightRaw = game.mCamera.getHeightRaw();
        this.mMenuAnimator.prepareAnimations(this.mMenuItems, cameraWidthRaw, cameraHeightRaw);
    }

    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final ITouchArea pTouchArea, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        if (null==pTouchArea) return true;
        if (pTouchArea instanceof  IMenuItem) {
            final IMenuItem menuItem = ((IMenuItem)pTouchArea);

            switch(pSceneTouchEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    if(this.mSelectedMenuItem != null && this.mSelectedMenuItem != menuItem) {
                        this.mSelectedMenuItem.onUnselected();
                    }
                    this.mSelectedMenuItem = menuItem;
                    this.mSelectedMenuItem.onSelected();
                    break;
                case MotionEvent.ACTION_UP:
                    final boolean handled = onItemClicked(hud, menuItem, pTouchAreaLocalX, pTouchAreaLocalY);
                    menuItem.onUnselected();
                    this.mSelectedMenuItem = null;
                    return handled;
                case MotionEvent.ACTION_CANCEL:
                    menuItem.onUnselected();
                    this.mSelectedMenuItem = null;
                    break;
            }
        }
        return true;
    }

    private boolean onItemClicked(final Scene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
        switch(pMenuItem.getID()) {
            case RESUME:
                hud.unregisterTouchArea(resumeMenuItem);
                hud.detachChild(resumeMenuItem);
                hud.unregisterTouchArea(exitMenuItem);
                hud.detachChild(exitMenuItem);
                hud.setOnAreaTouchListener((GameScene)parent);
                return true;
            case EXIT:
                parent.back();
                return true;
            default:
                return false;
        }
    }
}
