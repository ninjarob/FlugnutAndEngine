package com.pkp.flugnut.FlugnutAndEngine;

import android.os.PowerManager.WakeLock;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
import com.pkp.flugnut.FlugnutAndEngine.screen.global.MainMenuScene;
import com.pkp.flugnut.FlugnutAndEngine.utils.LevelXmlParser;
import com.pkp.flugnut.FlugnutAndEngine.utils.NavigationElements;
import com.pkp.flugnut.FlugnutAndEngine.utils.NavigationRedirect;
import org.andengine.audio.music.Music;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class GLGame extends SimpleBaseGameActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    enum GLGameState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }

    long startTime = System.nanoTime();
    WakeLock wakeLock;

    public static final int CAMERA_WIDTH = 533;
    public static final int CAMERA_HEIGHT = 800;


    // ===========================================================
    // Fields
    // ===========================================================
    public Camera mCamera;

    private BaseGameScene mScene;

    private PhysicsWorld mPhysicsWorld;

    public Music mMusic;

    GLGameState state = GLGameState.Initialized;
    Object stateChanged = new Object();
    private NavigationElements navigationElements;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public EngineOptions onCreateEngineOptions() {
        this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        // getStartScene();
        EngineOptions eo = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
        eo.getAudioOptions().setNeedsMusic(true);
        return eo;
    }

    @Override
    public void onCreateResources() {
        getStartScene();
        mScene.initResources();
        navigationElements = new NavigationElements();
        NavigationRedirect.initInstance(getApplicationContext());
        LevelXmlParser.initInstance(getApplicationContext());
    }

    @Override
    public Scene onCreateScene() {
        //this.mEngine.registerUpdateHandler(new FPSLogger());
        getStartScene();
        mScene.initScene();
        return this.mScene;
    }

    public void setNewScene(BaseGameScene newScene) {
        if (newScene == null)
            throw new IllegalArgumentException("Scene must not be null");
        this.mScene.detachChildren();
        this.mScene.dispose();
        newScene.initResources();
        newScene.initScene();
        this.mScene = newScene;
        mEngine.setScene(newScene);
    }

    public BaseGameScene getCurrentScene() {
        return mScene;
    }

    public BaseGameScene getStartScene() {
        if (mScene == null) {
            mScene = new MainMenuScene(this);
        }
        return mScene;
    }

    public NavigationElements getNavigationElements() {
        return navigationElements;
    }
}
