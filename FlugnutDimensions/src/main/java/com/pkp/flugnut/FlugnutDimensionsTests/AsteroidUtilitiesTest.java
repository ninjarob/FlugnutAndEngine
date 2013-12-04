package com.pkp.flugnut.FlugnutDimensionsTests;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.pkp.flugnut.FlugnutDimensions.gameObject.npc.Asteroid;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidInfo;
import com.pkp.flugnut.FlugnutDimensions.utils.AsteroidUtilities;
import com.pkp.flugnut.FlugnutDimensions.utils.Utilities;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.IObjectFactory;
import org.testng.annotations.ObjectFactory;

import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 12/3/13
 * Time: 8:31 AM
 * To change this template use File | Settings | File Templates.
 */
@PrepareForTest(Utilities.class)
public class AsteroidUtilitiesTest {

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

    @Mock
    private AsteroidInfo ai;

    @Mock
    private Asteroid asteroid;

    @Mock
    private Body body;

    @BeforeMethod
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Utilities.class);
    }

    @Test
    public void testAddAsteroid() throws Exception {

    }

    @Test
    public void testUpdateAsteroid() throws Exception {

    }

    @Test
    public void testRemoveAsteroid() throws Exception {

    }

    @Test
    public void testGetAsteroidRevoluteJointDef() throws Exception {

    }

    @Test
    public void testGetAsteroidFasterAheadPositiveVelocity() throws Exception {
        when(ai.getAsteroid()).thenReturn(asteroid);
        when(asteroid.getBody()).thenReturn(body);
        when(ai.getVelMag()).thenReturn(1f);
        Vector2 pos = new Vector2(1,0);
        when(body.getPosition()).thenReturn(pos);
        float faster = AsteroidUtilities.getOrbitalAsteroidFaster(0f, 1f, ai, true);
        Assert.assertTrue(faster < 0);
    }

    @Test
    public void testGetAsteroidFasterAheadPositiveVelocityAcrossEquator() throws Exception {
        when(ai.getAsteroid()).thenReturn(asteroid);
        when(asteroid.getBody()).thenReturn(body);
        when(ai.getVelMag()).thenReturn(1f);
        Vector2 pos = new Vector2(1,-.1f);
        when(body.getPosition()).thenReturn(pos);
        float faster = AsteroidUtilities.getOrbitalAsteroidFaster(1f, .1f, ai, true);
        Assert.assertTrue(faster < 0);
    }
}
