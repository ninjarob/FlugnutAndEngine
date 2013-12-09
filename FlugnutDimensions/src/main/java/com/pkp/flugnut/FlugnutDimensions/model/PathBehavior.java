package com.pkp.flugnut.FlugnutDimensions.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 12/7/13
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PathBehavior {

    public void pathUpdate(AsteroidInfo asteroidInfo, Vector2 pos);

    public void clean();
}
