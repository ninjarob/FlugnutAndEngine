package com.pkp.flugnut.FlugnutAndEngine.sprites;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 7/11/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class FlugnutShieldSprite extends Sprite {

    public FlugnutShieldSprite(float x, float y, ITextureRegion textureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
        super(x, y, textureRegion, vertexBufferObjectManager);
        setAlpha(.4f);
    }
}