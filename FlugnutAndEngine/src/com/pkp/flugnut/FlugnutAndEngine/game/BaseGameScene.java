package com.pkp.flugnut.FlugnutAndEngine.game;

import org.andengine.audio.music.MusicManager;
import org.andengine.entity.scene.Scene;
import com.pkp.flugnut.FlugnutAndEngine.GLGame;

public abstract class BaseGameScene extends Scene {
	public static float SSC; //SCREEN_SIZE_CONSTANT
	protected double timePassed = 0;
	protected boolean transitionIn;
	protected boolean transitionOut;
    protected GLGame game;
	public enum ScreenType{
		MainMenu,
		Game,
		Help,
		Story,
		Map
	}
	protected ScreenType nextScreen;


	public BaseGameScene(GLGame game) {
		//SSC = ((float)(height+width))/853f;
        this.game = game;
		transitionIn = true;
		transitionOut = false;
	}

    public abstract void initResources();
    public abstract void initScene();

	public void update(float deltaTime)
	{
		if (transitionIn) {
			//transitionIn = transitionIn(deltaTime);
		}
		else if (transitionOut) {
			//transitionOut = transitionOut(deltaTime);
			if (!transitionOut) {
				if (nextScreen != null) {
					//Utilities.setTheNextScreen((GLGame) game, nextScreen);
				}
				else {
					System.out.println("here");
				}
			}
		}
	}
}
