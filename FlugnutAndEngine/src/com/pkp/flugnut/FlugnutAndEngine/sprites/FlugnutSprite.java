package com.pkp.flugnut.FlugnutAndEngine.sprites;

import com.badlogic.gdx.physics.box2d.Body;
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
public class FlugnutSprite extends Sprite {

    public FlugnutSprite(float x, float y, ITextureRegion textureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
        super(x, y, textureRegion, vertexBufferObjectManager);

    }
}
