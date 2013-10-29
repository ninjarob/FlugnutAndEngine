package com.pkp.flugnut.FlugnutDimensions.screen.global;

import android.hardware.SensorManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.gameObject.*;
import com.pkp.flugnut.FlugnutDimensions.game.BaseGameScene;
import com.pkp.flugnut.FlugnutDimensions.level.GameSceneInfo;
import com.pkp.flugnut.FlugnutDimensions.level.Wave;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import com.pkp.flugnut.FlugnutDimensions.utils.GameUpdateHandler;
import com.pkp.flugnut.FlugnutDimensions.utils.Utilities;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScene extends BaseGameScene implements IOnSceneTouchListener, IOnAreaTouchListener {

    // ===========================================================
    // Fields
    // ===========================================================
    SpriteBackground normalBackground;
    VertexBufferObjectManager vertexBufferObjectManager;

    private BitmapTextureAtlas backgroundTexture;

    private ITextureRegion mapBackground;

    private BitmapTextureAtlas mBitmapTextureAtlas;

    private ITextureRegion buttonTextureRegion;
    private ITextureRegion pauseButtonTextureRegion;
    private Font mFont;

    private GameSceneInfo gameSceneInfo;
    private Scene self;

    private PauseMenu pauseMenu;

    public boolean tutorial;

    private PhysicsWorld physicsWorld;

    private Ship gawainShip;
    //    static final float MAX_FLIGHT_SPEED = 50.0f;
//    static final float ACCELERATION = 800;
//    static final float CLOSE_ENOUGH_THRESHOLD = 5;
//    static final float START_DEACCELL_DISTANCE = 150;
    public static Vector2 start = new Vector2();
    public static Vector2 empWave = new Vector2();
    public static Vector2 horizEmp = new Vector2();
    public List<Wave> waves = new ArrayList<Wave>();
    public float totalWaveTime;
    public List<MiscObject> miscObjects;
    //    public List<Bomb> bombs;
//    public List<EMPCircle> empCircles;
//    public List<EMPSuperCircle> empSuperCircles;
//    public List<EMPLauncher> empLaunchers;
//    public List<EMPHoriz> horizEmps;
//    public List<EMPZigZag> empZigZags;
    public boolean gameOver = false;
    public boolean gameWin = false;
    public int score = 0;
    public int weaponsUsed = 0;
    public GLGame game;
    public boolean empCircleFired = false;
    public boolean empSuperCircleFired = false;
    public boolean horizEmpFired = false;
    public boolean zigZagEmpFired = false;
    public static List<Body> bodiesToDestroy = new ArrayList<Body>();
    public static List<Joint> jointsToDestroy = new ArrayList<Joint>();
    public float worldTime = 0;

    public Rectangle leftWall;
    public Rectangle rightWall;


    private boolean birdAdded = false;
    //physics
//    public World world;

    private List<GameObject> gameObjects;
    private GameUpdateHandler guh;
    private MouseJoint mMouseJointActive;

    public GameScene(GLGame game, GameSceneInfo gameSceneInfo, boolean tutorial) {
        super(game);
        this.gameSceneInfo = gameSceneInfo;
        self = this;
        this.tutorial = tutorial;

        this.game = game;
//        bombs = new ArrayList<Bomb>();
//        empCircles = new ArrayList<EMPCircle>();
//        empSuperCircles = new ArrayList<EMPSuperCircle>();
//        empLaunchers = new ArrayList<EMPLauncher>();
//        horizEmps = new ArrayList<EMPHoriz>();
//        empZigZags = new ArrayList<EMPZigZag>();
        empWave.x = 0;
        empWave.y = 0;
        horizEmp.x = 0;
        horizEmp.y = 0;
        miscObjects = new ArrayList<MiscObject>();
        bodiesToDestroy = new ArrayList<Body>();
        totalWaveTime = 0;
        if (waves.size() > 0)
        {
            totalWaveTime = waves.get(waves.size()-1).startTime;
        }
        gameObjects = new ArrayList<GameObject>();
    }

    @Override
    public void initResources() {
        //FONT
        FontFactory.setAssetBasePath("font/");
        final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.mFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(), "Droid.ttf", 16, true, android.graphics.Color.WHITE);
        this.mFont.load();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        //BACKGROUND
        this.backgroundTexture = new BitmapTextureAtlas(game.getTextureManager(), 800, 1200);
        this.mapBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backgroundTexture, game, gameSceneInfo.getBgFileName(), 0, 0);
        this.backgroundTexture.load();

        initLevel1();

        this.mBitmapTextureAtlas.load();
    }

    public void initLevel1() {
        // w    h
        //129, 226 buttons        starty = 0, endy = 226
        //448, 448 gawain         starty = 226, endy = 674
        //448, 448 gawain_engine  starty = 674, endy = 1122



        this.mBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 450, 675, TextureOptions.DEFAULT);
        this.buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "buttons.png", 0, 0);
        this.pauseButtonTextureRegion = TextureRegionFactory.extractFromTexture(mBitmapTextureAtlas, 64, 128, 64, 64);

        gawainShip = new GawainShip(this, 226, 674);
        gawainShip.initResources("Ship/gawain.png", "Ship/gawain_engine.png", mBitmapTextureAtlas);
        gawainShip.initSprites(vertexBufferObjectManager);

//        FlugnutShield flugnutShield = new FlugnutShield(this, 395);
//        flugnutShield.initResources("emp1.png", mBitmapTextureAtlas);
//        flugnutShield.initSprites(vertexBufferObjectManager);
//
//        //remember, the origin is in the center.
//        Flugnut flugnut = new Flugnut(this, 226, flugnutShield);
//        flugnut.initResources("Flugnut.png", mBitmapTextureAtlas);
//        flugnut.initSprites(vertexBufferObjectManager);
//        flugnutShield.setFlugnut(flugnut);
//
//        BlockBuilding b = new BlockBuilding(this, 475, 239, 120, 10, 10);
//        b.initResources("FlugnutLevel/House.png", mBitmapTextureAtlas);
//        b.initSprites(vertexBufferObjectManager);
//
//        //scaled by 1/12
//        Pylon pylon1 = new Pylon(this, 713, 79, 67);
//        pylon1.initResources("Anim/pylonAnim.png", mBitmapTextureAtlas);
//        pylon1.initSprites(vertexBufferObjectManager);
//        //Pylon plyon2;
//        //Flag flag;
//
//        //set start positions
        gawainShip.setStartPosition(new Vector2((GLGame.CAMERA_WIDTH/2)+(gawainShip.getSprite().getWidthScaled()/2),
                                                    100+(gawainShip.getSprite().getHeightScaled()/2)));
//        flugnut.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH/2, 100));
//        b.setStartPosition(new Vector2((b.getSprite().getWidth()/2) + 100, GLGame.CAMERA_HEIGHT-(b.getSprite().getHeight()/2)-50));
//        pylon1.setStartPosition(new Vector2(100, 100));
//        gameObjects.add(b);
//        gameObjects.add(flugnutShield);
        gameObjects.add(gawainShip);
//        gameObjects.add(pylon1);
        guh = new GameUpdateHandler(game, gameObjects, this);
    }

    @Override
    public void initScene() {
        vertexBufferObjectManager = game.getVertexBufferObjectManager();

        //BACKGROUND
        Sprite backgroundSprite = new Sprite(0, 0, mapBackground, vertexBufferObjectManager);
        backgroundSprite.setScale(2.4f);
        normalBackground = new SpriteBackground(0, 0, 0, backgroundSprite);
        setBackground(normalBackground);

        //physics
        this.physicsWorld = new FixedStepPhysicsWorld(30, new Vector2(0, 0), false, 3, 2);

        //boundaries
        final Rectangle ground = new Rectangle(-400, GLGame.CAMERA_HEIGHT, GLGame.CAMERA_WIDTH+800, 2, vertexBufferObjectManager);
        final Rectangle roof = new Rectangle(-400, 0, GLGame.CAMERA_WIDTH+800, 2, vertexBufferObjectManager);
        leftWall = new Rectangle(-400, -20, 2, GLGame.CAMERA_HEIGHT+40, vertexBufferObjectManager);
        rightWall = new Rectangle(GLGame.CAMERA_WIDTH - 2+400, -20, 2, GLGame.CAMERA_HEIGHT+40, vertexBufferObjectManager);

        final Rectangle left = new Rectangle(-20, -20, 2, GLGame.CAMERA_HEIGHT, vertexBufferObjectManager);
        final Rectangle right = new Rectangle(GLGame.CAMERA_WIDTH - 2+20, 20, 2, GLGame.CAMERA_HEIGHT, vertexBufferObjectManager);


        PhysicsFactory.createBoxBody(physicsWorld, ground, BodyDef.BodyType.StaticBody, GameConstants.WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(physicsWorld, roof, BodyDef.BodyType.StaticBody, GameConstants.WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(physicsWorld, leftWall, BodyDef.BodyType.StaticBody, GameConstants.WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(physicsWorld, rightWall, BodyDef.BodyType.StaticBody, GameConstants.WALL_FIXTURE_DEF);
        attachChild(ground);
        attachChild(roof);
        attachChild(leftWall);
        attachChild(rightWall);

        //listeners
        setOnAreaTouchListener(this);
        setOnSceneTouchListener(this);

        //pause button
        final Sprite pauseButton = new Sprite(10, 74, pauseButtonTextureRegion, vertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (!game.isGamePaused()) {
                    pauseMenu = new PauseMenu(game, self);
                    pauseMenu.initResources();
                    pauseMenu.initMenu();
                }
                return true;
            }
        };
        attachChild(pauseButton);
        registerTouchArea(pauseButton);
        setTouchAreaBindingOnActionDownEnabled(true);


        for (GameObject o : gameObjects) {
            o.initForScene(physicsWorld);
        }

        registerUpdateHandler(physicsWorld);
        registerUpdateHandler(guh);
    }

    public Rectangle getLeftWall() {
        return leftWall;
    }

    public Rectangle getRightWall() {
        return rightWall;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public void back() {
        if (tutorial) {
            game.setNewScene(new TutorialSelectionScene(game));
        }
        else {
            game.setNewScene(new MapScene(game));
        }
    }


    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        final Sprite flugnutShieldSprite = (Sprite) pTouchArea;
        //FlugnutShield fs = ((FlugnutShield)flugnutShieldSprite.getUserData());
        switch(pSceneTouchEvent.getAction()) {
            case TouchEvent.ACTION_DOWN:
                //fs.getBody().setLinearDamping(1);
                //fs.getFlugnut().getBody().setLinearDamping(.5f);
                /*
                 * If we have a active MouseJoint, we are just moving it around
                 * instead of creating a second one.
                 */
                if(this.mMouseJointActive == null) {
                    this.mMouseJointActive = Utilities.createMouseJoint(flugnutShieldSprite, pTouchAreaLocalX, pTouchAreaLocalY, physicsWorld);
                }
                return true;
            case TouchEvent.ACTION_MOVE:
                if(this.mMouseJointActive != null) {
                    final Vector2 vec = Vector2Pool.obtain(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
                    this.mMouseJointActive.setTarget(vec);
                    Vector2Pool.recycle(vec);
                }
                return true;
            case TouchEvent.ACTION_UP:
                //final Vector2 vec = Vector2Pool.obtain(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
                //if (flugnut.contains(vec.x, vec.y)) {  //only destroy the joint if its a flick within the flugnut body.
                //releaseMouseJoint(fs);
                //}
                return true;
        }
        return false;
    }

//    public void releaseMouseJoint(FlugnutShield fs) {
//        if(this.mMouseJointActive != null) {  //we've got the move joint!  Now get rid of it.
//            this.physicsWorld.destroyJoint(this.mMouseJointActive);
//            this.mMouseJointActive = null;
//            fs.getBody().setLinearDamping(4);
//            fs.getFlugnut().getBody().setLinearDamping(4);
//        }
//    }
//
//    public void resetMouseJoint(FlugnutShield fs) {
//        final Sprite flugnutShieldSprite = fs.getSprite();
//        fs.getBody().setLinearDamping(1);
//        fs.getFlugnut().getBody().setLinearDamping(.5f);
//                /*
//                 * If we have a active MouseJoint, we are just moving it around
//                 * instead of creating a second one.
//                 */
//        if(this.mMouseJointActive == null) {
//            this.mMouseJointActive = Utilities.createMouseJoint(flugnutShieldSprite, fs.getStartPosition().x, fs.getStartPosition().y, physicsWorld);
//        }
//    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if(this.physicsWorld != null) {
            switch(pSceneTouchEvent.getAction()) {
                case TouchEvent.ACTION_DOWN:
                    final Vector2 touchLoc = Vector2Pool.obtain(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
                    Vector2 shipLoc = new Vector2(gawainShip.getSprite().getX(), gawainShip.getSprite().getY());
                    float angle = Utilities.getAngle(touchLoc, shipLoc);
                    gawainShip.rotateShip(gawainShip.getDestIndex(angle));
                    return true;
                case TouchEvent.ACTION_MOVE:
                    return true;
                case TouchEvent.ACTION_UP:
                    return true;
            }
            return false;
        }
        return false;
    }
}
