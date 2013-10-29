package com.pkp.flugnut.FlugnutDimensions.game;


public interface Swipeable {
	public static final float TRANSITION_SPEED = 800;
	
	/**
	 * transitionIn
	 * @param deltaTime
	 * @return boolean still transitioning = true
	 */
	public boolean transitionIn(float deltaTime);
	
	/**
	 * transitionIn
	 * @param deltaTime
	 * @return boolean still transitioning = true
	 */
	public boolean transitionOut(float deltaTime);
}
