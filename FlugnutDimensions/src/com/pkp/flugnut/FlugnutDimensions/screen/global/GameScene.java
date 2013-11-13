package com.pkp.flugnut.FlugnutDimensions.screen.global;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.client.SmartFoxBase;
import com.pkp.flugnut.FlugnutDimensions.game.BaseGameScene;
import com.pkp.flugnut.FlugnutDimensions.game.ConnectionStatus;
import com.pkp.flugnut.FlugnutDimensions.game.GameTextureAtlasManager;
import com.pkp.flugnut.FlugnutDimensions.game.TextureType;
import com.pkp.flugnut.FlugnutDimensions.gameObject.*;
import com.pkp.flugnut.FlugnutDimensions.level.GameSceneInfo;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidArea;
import com.pkp.flugnut.FlugnutDimensions.gameObject.CelestialBody;
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

import java.util.ArrayList;
import java.util.List;

public class GameScene extends BaseGameScene implements IOnSceneTouchListener, IOnAreaTouchListener {

    // ===========================================================
    // Fields
    // ===========================================================

    private Font mFont;

    private GameSceneInfo gameSceneInfo;

    private PhysicsWorld physicsWorld;

    private Ship ship;
    public static Vector2 empWave = new Vector2();
    public static Vector2 horizEmp = new Vector2();
    public List<Asteroid> asteroids = new ArrayList<Asteroid>();
    public List<CelestialBody> stationaryBodies = new ArrayList<CelestialBody>();
    public GLGame game;

    public Rectangle leftWall;
    public Rectangle rightWall;

    private List<GameObject> gameObjects;
    private GameUpdateHandler guh;

    private HUD hud;

    private SmartFoxBase sfb;

    public GameScene(GLGame game, GameSceneInfo gameSceneInfo, SmartFoxBase sfb) {
        super(game);
        this.gameSceneInfo = gameSceneInfo;

        this.game = game;
        this.sfb = sfb;
        if (ConnectionStatus.Status.CONNECTED!=sfb.getStatus().getStatus()) {
            sfb.connect();
        }
        empWave.x = 0;
        empWave.y = 0;
        horizEmp.x = 0;
        horizEmp.y = 0;
        gameObjects = new ArrayList<GameObject>();
    }

    @Override
    public void initResources() {
        //FONT
        FontFactory.setAssetBasePath("font/");
        final ITexture fontTexture = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.mFont = FontFactory.createFromAsset(game.getFontManager(), fontTexture, game.getAssets(), "Droid.ttf", 16, true, android.graphics.Color.WHITE);
        this.mFont.load();

        //init background resource
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        gameSceneInfo.getBackgroundTexture().load();

        //init ship resource
        ship = gameSceneInfo.getShip();
        ship.setScene(this);
        gameObjects.add(ship);
        game.mCamera.setChaseEntity(ship.getSprite());

        //init hud
        GameTextureAtlasManager gtam = new GameTextureAtlasManager();
        gtam.addTexture("throttle.png", TextureType.THROTTLE, 30, 220);
        gtam.addTexture("throttle_ind.png", TextureType.THROTTLE_IND, 20, 4);
        gtam.addTexture("throttleButtons.png", TextureType.THROTTLE_BUTTON, 128, 70);
        BitmapTextureAtlas hudBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), gtam.getWidth()+10, gtam.getHeight()+10, TextureOptions.DEFAULT);
        hud = new HUD();
        initHudThrustButtonsResources(gtam, hudBitmapTextureAtlas, hud);
        //initHudThrustBarResources();
        initHudZoomBarResources(gtam, hudBitmapTextureAtlas, hud);
        hudBitmapTextureAtlas.load();
        game.mCamera.setHUD(hud);

        //init updatehandler
        guh = new GameUpdateHandler(game, gameObjects, this);
        gameSceneInfo.getBitMapTextureAtlas().load();
    }

    public void initHudThrustButtonsResources(GameTextureAtlasManager gtam, BitmapTextureAtlas hudBitmapTextureAtlas, HUD hud) {
        List<ThrottleButton> tbs = new ArrayList<ThrottleButton>();
        ThrottleButton tb1 = new ThrottleButton(game, hud, gtam.getTextureInfoHolder(TextureType.THROTTLE_BUTTON), ship, 0);
        ThrottleButton tb2 = new ThrottleButton(game, hud, gtam.getTextureInfoHolder(TextureType.THROTTLE_BUTTON), ship, 1);
        ThrottleButton tb3 = new ThrottleButton(game, hud, gtam.getTextureInfoHolder(TextureType.THROTTLE_BUTTON), ship, 2);
        ThrottleButton tb4 = new ThrottleButton(game, hud, gtam.getTextureInfoHolder(TextureType.THROTTLE_BUTTON), ship, 3);
        tb1.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH - 60, GLGame.CAMERA_HEIGHT - 80));
        tb2.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH - 60, GLGame.CAMERA_HEIGHT - 130));
        tb3.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH - 60, GLGame.CAMERA_HEIGHT - 180));
        tb4.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH - 60, GLGame.CAMERA_HEIGHT - 230));
        tb1.initResources(hudBitmapTextureAtlas);
        tb1.initSprites(gameSceneInfo.getVertexBufferObjectManager());
        tb2.initResources(hudBitmapTextureAtlas);
        tb2.initSprites(gameSceneInfo.getVertexBufferObjectManager());
        tb3.initResources(hudBitmapTextureAtlas);
        tb3.initSprites(gameSceneInfo.getVertexBufferObjectManager());
        tb4.initResources(hudBitmapTextureAtlas);
        tb4.initSprites(gameSceneInfo.getVertexBufferObjectManager());
        tbs.add(tb1);
        tbs.add(tb2);
        tbs.add(tb3);
        tbs.add(tb4);
        tb1.setThrottleButtonList(tbs);
        tb2.setThrottleButtonList(tbs);
        tb3.setThrottleButtonList(tbs);
        tb4.setThrottleButtonList(tbs);

        gameObjects.add(tb1);
        gameObjects.add(tb2);
        gameObjects.add(tb3);
        gameObjects.add(tb4);
    }

    public void initHudZoomBarResources(GameTextureAtlasManager gtam, BitmapTextureAtlas hudBitmapTextureAtlas, HUD hud) {
        ThrottleBarInd throttleBarInd = new ThrottleBarInd(game, hud, gtam.getTextureInfoHolder(TextureType.THROTTLE_IND));
        throttleBarInd.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH - 95, GLGame.CAMERA_HEIGHT - 24));

        ZoomThrottle zoomThrottle = new ZoomThrottle(game, hud, gtam.getTextureInfoHolder(TextureType.THROTTLE), throttleBarInd);
        zoomThrottle.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH - 100 , GLGame.CAMERA_HEIGHT - 230));
        zoomThrottle.initResources(hudBitmapTextureAtlas);
        zoomThrottle.initSprites(gameSceneInfo.getVertexBufferObjectManager());

        throttleBarInd.initResources(hudBitmapTextureAtlas);
        throttleBarInd.initSprites(gameSceneInfo.getVertexBufferObjectManager());

        gameObjects.add(zoomThrottle);
        gameObjects.add(throttleBarInd);
    }

    public void initHudThrustBarResources(GameTextureAtlasManager gtam, BitmapTextureAtlas hudBitmapTextureAtlas, HUD hud) {
        ThrottleBarInd throttleBarInd = new ThrottleBarInd(game, hud, gtam.getTextureInfoHolder(TextureType.THROTTLE_IND));
        throttleBarInd.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH - 45, GLGame.CAMERA_HEIGHT - 24));

        ThrustThrottle thrustThrottle = new ThrustThrottle(game, hud, gtam.getTextureInfoHolder(TextureType.THROTTLE), throttleBarInd, ship);
        thrustThrottle.setStartPosition(new Vector2(GLGame.CAMERA_WIDTH - 50 , GLGame.CAMERA_HEIGHT - 230));
        thrustThrottle.initResources(hudBitmapTextureAtlas);
        thrustThrottle.initSprites(gameSceneInfo.getVertexBufferObjectManager());

        throttleBarInd.initResources(hudBitmapTextureAtlas);
        throttleBarInd.initSprites(gameSceneInfo.getVertexBufferObjectManager());

        gameObjects.add(thrustThrottle);
        gameObjects.add(throttleBarInd);
    }



    @Override
    public void initScene() {

        //BACKGROUND
        Sprite backgroundSprite = new Sprite(0, 0, gameSceneInfo.getMapBackground(), gameSceneInfo.getVertexBufferObjectManager());
        backgroundSprite.setScale(2.4f);
        SpriteBackground normalBackground = new SpriteBackground(0, 0, 0, backgroundSprite);
        setBackground(normalBackground);

        //physics
        this.physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 0), false, 8, 8);

        //boundaries
        final Rectangle ground = new Rectangle(-1*gameSceneInfo.getSystemRadius(), gameSceneInfo.getSystemRadius(), 2*gameSceneInfo.getSystemRadius(), 2, gameSceneInfo.getVertexBufferObjectManager());
        final Rectangle roof = new Rectangle(-1*gameSceneInfo.getSystemRadius(), -1*gameSceneInfo.getSystemRadius(), 2*gameSceneInfo.getSystemRadius(), 2, gameSceneInfo.getVertexBufferObjectManager());
        leftWall = new Rectangle(-1*gameSceneInfo.getSystemRadius(), -gameSceneInfo.getSystemRadius(), 2, 2*gameSceneInfo.getSystemRadius(), gameSceneInfo.getVertexBufferObjectManager());
        rightWall = new Rectangle(gameSceneInfo.getSystemRadius(), -gameSceneInfo.getSystemRadius(), 2, 2*gameSceneInfo.getSystemRadius(), gameSceneInfo.getVertexBufferObjectManager());

        PhysicsFactory.createBoxBody(physicsWorld, ground, BodyDef.BodyType.StaticBody, GameConstants.WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(physicsWorld, roof, BodyDef.BodyType.StaticBody, GameConstants.WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(physicsWorld, leftWall, BodyDef.BodyType.StaticBody, GameConstants.WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(physicsWorld, rightWall, BodyDef.BodyType.StaticBody, GameConstants.WALL_FIXTURE_DEF);
        attachChild(ground);
        attachChild(roof);
        attachChild(leftWall);
        attachChild(rightWall);

        //asteroid area boundries
        for(AsteroidArea aa : gameSceneInfo.getAsteroidAreas()) {
            aa.initAreaOnGame(this, physicsWorld, gameSceneInfo.getVertexBufferObjectManager());
        }

        for (CelestialBody sb : gameSceneInfo.getCelestialBodies()) {
            attachChild(sb.getSprite());
        }

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

    // ===========================================================
    // Methods
    // ===========================================================

    public void back() {
        game.setNewScene(new MainMenuScene(game));
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        final Sprite sprite = (Sprite) pTouchArea;
        GameObject touchedObj = (GameObject)(sprite.getUserData());
        if (touchedObj.isTouchable()) {
            switch(pSceneTouchEvent.getAction()) {
                case TouchEvent.ACTION_DOWN:
                    touchedObj.onActionDown(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), physicsWorld);
                    return true;
                case TouchEvent.ACTION_MOVE:
                    touchedObj.onActionMove(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), physicsWorld);
                    return true;
                case TouchEvent.ACTION_UP:
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

    public HUD getHud() {
        return hud;
    }
}
