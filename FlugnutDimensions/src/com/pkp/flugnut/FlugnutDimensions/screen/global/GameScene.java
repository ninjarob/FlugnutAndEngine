package com.pkp.flugnut.FlugnutDimensions.screen.global;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.client.SmartFoxBase;
import com.pkp.flugnut.FlugnutDimensions.gameObject.*;
import com.pkp.flugnut.FlugnutDimensions.game.BaseGameScene;
import com.pkp.flugnut.FlugnutDimensions.level.GameSceneInfo;
import com.pkp.flugnut.FlugnutDimensions.level.Wave;
import com.pkp.flugnut.FlugnutDimensions.sprites.PauseButtonSprite;
import com.pkp.flugnut.FlugnutDimensions.utils.GameConstants;
import com.pkp.flugnut.FlugnutDimensions.utils.GameUpdateHandler;
import com.pkp.flugnut.FlugnutDimensions.utils.Utilities;
import org.andengine.engine.camera.hud.HUD;
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

    private Ship ship;
    public static Vector2 empWave = new Vector2();
    public static Vector2 horizEmp = new Vector2();
    public List<Asteroid> asteroids = new ArrayList<Asteroid>();
    public List<MiscObject> miscObjects;
    public GLGame game;

    public Rectangle leftWall;
    public Rectangle rightWall;

    private List<GameObject> gameObjects;
    private GameUpdateHandler guh;

    private HUD hud;

    private SmartFoxBase sfb;

    private Integer asteroidNumber;

    public GameScene(GLGame game, GameSceneInfo gameSceneInfo, boolean tutorial, SmartFoxBase sfb) {
        super(game);
        this.gameSceneInfo = gameSceneInfo;
        self = this;
        this.tutorial = tutorial;

        this.game = game;
        this.sfb = sfb;
        if (SmartFoxBase.Status.CONNECTED!=sfb.getStatus()) {
            sfb.connect();
        }
        empWave.x = 0;
        empWave.y = 0;
        horizEmp.x = 0;
        horizEmp.y = 0;
        miscObjects = new ArrayList<MiscObject>();
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
        //2016, 560 asteroid1     starty = 1122, endy = 1682
        //20, 200 throttle        starty = 1682, endy = 1882
        //20, 4   throttle_ind    starty = 1882, 1886


        this.mBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 2020, 1886, TextureOptions.DEFAULT);
        this.buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "buttons.png", 0, 0);
        this.pauseButtonTextureRegion = TextureRegionFactory.extractFromTexture(mBitmapTextureAtlas, 64, 128, 64, 64);

        ship = new GawainShip(game, this, 226, 674);
        ship.initResources("Ship/gawain.png", mBitmapTextureAtlas);
        ship.initSprites(vertexBufferObjectManager);

        for (int i = 0; i < asteroidNumber; i++) {
            Asteroid asteroid = new Asteroid(game, this, 1122);
            asteroid.initResources("asteroid.png", mBitmapTextureAtlas);
            asteroid.initSprites(vertexBufferObjectManager);
            asteroids.add(asteroid);
        }

        hud = new HUD();

        ThrottleInd throttleInd = new ThrottleInd(game, hud, 1882);
        throttleInd.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH - 30 , GLGame.CAMERA_HEIGHT - 10));

        Throttle throttle = new Throttle(game, hud, 1682, throttleInd, ship);
        throttle.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH - 30 , GLGame.CAMERA_HEIGHT - 210));
        throttle.initResources("throttle.png", mBitmapTextureAtlas);
        throttle.initSprites(vertexBufferObjectManager);

        throttleInd.initResources("throttle_ind.png", mBitmapTextureAtlas);
        throttleInd.initSprites(vertexBufferObjectManager);
        game.mCamera.setHUD(hud);


        ship.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH / 2, GLGame.CAMERA_HEIGHT / 2));
        game.mCamera.setChaseEntity(ship.getSprite());
        gameObjects.add(ship);
        for (Asteroid a : asteroids) {
            a.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH / 2, (GLGame.CAMERA_HEIGHT / 2)+120));
            gameObjects.add(a);
        }
        gameObjects.add(throttle);
        gameObjects.add(throttleInd);
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
        this.physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 0), false, 8, 8);

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
        hud.setOnAreaTouchListener(this);

        for (GameObject o : gameObjects) {
            o.initForScene(physicsWorld);
        }

        registerUpdateHandler(physicsWorld);
        registerUpdateHandler(guh);

        setTouchAreaBindingOnActionDownEnabled(true);
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
            game.setNewScene(new TutorialSelectionScene(game, sfb));
        }
        else {
            game.setNewScene(new MapScene(game));
        }
    }


    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        final Sprite sprite = (Sprite) pTouchArea;
        //FlugnutShield fs = ((FlugnutShield)flugnutShieldSprite.getUserData());
        GameObject touchedObj = (GameObject)(sprite.getUserData());
        if (touchedObj.isTouchable()) {
            switch(pSceneTouchEvent.getAction()) {
                case TouchEvent.ACTION_DOWN:
                    //fs.getBody().setLinearDamping(1);
                    //fs.getFlugnut().getBody().setLinearDamping(.5f);
                    /*
                     * If we have a active MouseJoint, we are just moving it around
                     * instead of creating a second one.
                     */
                    //if(this.mMouseJointActive == null) {
                        //this.mMouseJointActive = Utilities.createMouseJoint(flugnutShieldSprite, pTouchAreaLocalX, pTouchAreaLocalY, physicsWorld);
                    //}
                    touchedObj.onActionDown(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), physicsWorld);
                    return true;
                case TouchEvent.ACTION_MOVE:
//                    if(this.mMouseJointActive != null) {
//                        final Vector2 vec = Vector2Pool.obtain(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
//                        this.mMouseJointActive.setTarget(vec);
//                        Vector2Pool.recycle(vec);
//                    }
                    touchedObj.onActionMove(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), physicsWorld);
                    return true;
                case TouchEvent.ACTION_UP:
                    //final Vector2 vec = Vector2Pool.obtain(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
                    //if (flugnut.contains(vec.x, vec.y)) {  //only destroy the joint if its a flick within the flugnut body.
                    //releaseMouseJoint(fs);
                    //}
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if(physicsWorld != null) {
            switch(pSceneTouchEvent.getAction()) {
                case TouchEvent.ACTION_DOWN:
                case TouchEvent.ACTION_MOVE:
                    final Vector2 touchLoc = Vector2Pool.obtain(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
                    Vector2 shipLoc = Vector2Pool.obtain(ship.getSprite().getX()/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, ship.getSprite().getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
                    float angle = Utilities.getAngle(shipLoc, touchLoc);
                    ship.rotateShip(ship.getDestIndex(angle));
                    return true;
                case TouchEvent.ACTION_UP:
                    return true;
            }
            return false;
        }
        return false;
    }


    public Integer getAsteroidNumber() {
        return asteroidNumber;
    }

    public void setAsteroidNumber(Integer asteroidNumber) {
        this.asteroidNumber = asteroidNumber;
    }
}
