package com.pkp.flugnut.FlugnutAndEngine.screen;

import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
import com.pkp.flugnut.FlugnutAndEngine.utils.ScrollingSprite;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class AustinScene extends BaseGameScene {

    // ===========================================================
    // Fields
    // ===========================================================
    SpriteBackground normalBackground;
    VertexBufferObjectManager vertexBufferObjectManager;
	private BitmapTextureAtlas imageBitmapTextureAtlas;
    private ITextureRegion imageTextureRegion;
	private ScrollingSprite imageSprite;

	
    public AustinScene(GLGame game) {
        super(game);
    }

    @Override
    public void initResources() {
		// scroll image
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		imageBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 736, 391, TextureOptions.BILINEAR);
		// #NOTE : 736, 391 is the width, height of the view area of this view
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(imageBitmapTextureAtlas, game, "flugnut_title.png", 0, 0);
		// #NOTE : 0, 0 at the end of this call tells the position of the image
        imageBitmapTextureAtlas.load();
		
		ITextureRegion clippedRegion = TextureRegionFactory.extractFromTexture(imageBitmapTextureAtlas, 0, 10, 368, 195);
		imageSprite = new ScrollingSprite(0, 0, clippedRegion, game.getVertexBufferObjectManager()); 
		// #NOTE : 0, 0 is the location of the sprite

		// animate move the imageSprite
//		imageSprite.registerEntityModifier(new MoveModifier(100, 0, 200, 0, 200, EaseLinear.getInstance())); // this will move the whole image but how to move the clipped region making the image
		imageSprite.setPosition(100, 100);
		imageSprite.setX(50);
		
	}

    @Override
    public void initScene() {
		attachChild(imageSprite);
    }
	
	// when moving image
	// - create a new sprite of the full image offset by the scroll position
	// - show the new sprite over the original sprite
	// - remove the original sprite
}
