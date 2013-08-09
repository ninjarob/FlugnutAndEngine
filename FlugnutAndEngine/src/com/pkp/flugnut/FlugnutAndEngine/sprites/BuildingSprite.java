package com.pkp.flugnut.FlugnutAndEngine.sprites;

import com.pkp.flugnut.FlugnutAndEngine.model.level.CarriedObject;
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
public class BuildingSprite extends Sprite {

    public BuildingSprite(float x, float y, ITextureRegion textureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
        super(x, y, textureRegion, vertexBufferObjectManager);
    }
}
