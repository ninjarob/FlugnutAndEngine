package com.pkp.flugnut.FlugnutDimensions.gameObject.hud;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.TextureInfoHolder;
import com.pkp.flugnut.FlugnutDimensions.gameObject.AbstractGameObjectImpl;
import com.pkp.flugnut.FlugnutDimensions.gameObject.Ship;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 10/28/13
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThrottleButton extends AbstractGameObjectImpl {

    protected ITiledTextureRegion throttleTR;
    protected AnimatedSprite throttleSprite;
    private Ship ship;
    private HUD hud;
    private int throttleIndex;
    private List<ThrottleButton> throttleButtonList;

    public ThrottleButton(GLGame game, HUD hud, TextureInfoHolder textureInfoHolder, Ship ship, int throttleIndex) {
        super(game, textureInfoHolder);
        touchable = true;
        this.ship = ship;
        this.hud = hud;
        this.throttleIndex = throttleIndex;
    }

    @Override
    public Sprite getSprite() {
        return throttleSprite;
    }

    @Override
    public void initResources(BitmapTextureAtlas mBitmapTextureAtlas) {
        this.throttleTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, game, texInfo.getPath(), 0, texInfo.getStarty(), 2, 1);

    }

    @Override
    public void initSprites(VertexBufferObjectManager vertexBufferObjectManager) {
        throttleSprite = new AnimatedSprite(sp.x, sp.y, throttleTR, vertexBufferObjectManager);

        FontFactory.setAssetBasePath("font/");
        final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        Font mFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(), "Plok.ttf", 12, true, android.graphics.Color.WHITE);
        mFont.load();
        String text = "0%";
        switch(throttleIndex) {
            case 1:
                text = "33%";
                break;
            case 2:
                text = "66%";
                break;
            case 3:
                text = "100%";
                break;
        }

        Text buttonText = new Text(0, 0, mFont, text, vertexBufferObjectManager);
        buttonText.setPosition((throttleSprite.getWidth() - buttonText.getWidth()) / 2, (throttleSprite.getHeight() - buttonText.getHeight()) / 2);
        throttleSprite.setAlpha(.5f);
        throttleSprite.attachChild(buttonText);
    }

    @Override
    public void initForScene(PhysicsWorld physics) {
        throttleSprite.setUserData(this);
        throttleSprite.setScale(0.75f);
        hud.attachChild(throttleSprite);
        hud.registerTouchArea(throttleSprite);
    }

    private void updateThrottleAndThrust() {
        float thrustPercent = ((float)throttleIndex)/3;
        ship.setThrustPercent(thrustPercent);
        for (ThrottleButton tb : throttleButtonList)
        {
            ((AnimatedSprite)tb.getSprite()).setCurrentTileIndex(0);
        }
        throttleSprite.setCurrentTileIndex(1);
    }

    public List<ThrottleButton> getThrottleButtonList() {
        return throttleButtonList;
    }

    public void setThrottleButtonList(List<ThrottleButton> throttleButtonList) {
        this.throttleButtonList = throttleButtonList;
    }

    public void onActionDown(float touchX, float touchY, PhysicsWorld physicsWorld) {
        updateThrottleAndThrust();
    }

    public void onActionMove(float touchX, float touchY, PhysicsWorld physicsWorld) {
        //updateThrottleAndThrust(touchY);
    }
}
