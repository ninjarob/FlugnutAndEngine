package com.pkp.flugnut.FlugnutAndEngine.screen.global;

import android.hardware.SensorManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import com.pkp.flugnut.FlugnutAndEngine.Service.FlugnutService;
import com.pkp.flugnut.FlugnutAndEngine.Service.MiscObject;
import com.pkp.flugnut.FlugnutAndEngine.game.BaseGameScene;
import com.pkp.flugnut.FlugnutAndEngine.model.level.GameSceneInfo;
import com.pkp.flugnut.FlugnutAndEngine.model.level.Wave;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
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
import org.andengine.util.debug.Debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScene extends BaseGameScene implements IOnSceneTouchListener, IOnAreaTouchListener {

    // ===========================================================
    // Fields
    // ===========================================================
    public static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
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


    private boolean birdAdded = false;
    //physics
//    public World world;

    public FlugnutService flugnut;
    private MouseJoint mMouseJointActive;
    private Body mGroundBody;

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
        //flugnut = new Shwaiflugnut(game, 64 * IScreen.SSC, 64 * IScreen.SSC, "Newflugnut.png", this);
        bodiesToDestroy = new ArrayList<Body>();
        totalWaveTime = 0;
        if (waves.size() > 0)
        {
            totalWaveTime = waves.get(waves.size()-1).startTime;
        }
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

        //129, 226 buttons
        //131, 169 flugnut    260, 395
        //80,80  emp1          340, 475
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 340, 475, TextureOptions.BILINEAR);
        this.buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "buttons.png", 0, 0);
        this.pauseButtonTextureRegion = TextureRegionFactory.extractFromTexture(mBitmapTextureAtlas, 64, 128, 64, 64);

        flugnut = new FlugnutService(this);
        flugnut.initResources("Flugnut.png", mBitmapTextureAtlas, 226, 260);
        //flugnut.initResources("NewGuy.png", mBitmapTextureAtlas, 226);
//        waves = GenerateWorldObjects.generateWaves(gameScreen.gameSceneInfo.levelNum);
//        buildings = GenerateWorldObjects.generateBuildings(game, this, gameScreen.gameSceneInfo.levelNum);




        this.mBitmapTextureAtlas.load();
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
        this.physicsWorld = new FixedStepPhysicsWorld(30, new Vector2(0, SensorManager.GRAVITY_EARTH), false, 3, 2);
        this.mGroundBody = this.physicsWorld.createBody(new BodyDef());

        //boundaries
        final Rectangle ground = new Rectangle(-20, GLGame.CAMERA_HEIGHT - 2, GLGame.CAMERA_WIDTH+20, 2, vertexBufferObjectManager);
        final Rectangle roof = new Rectangle(-20, 0, GLGame.CAMERA_WIDTH+20, 2, vertexBufferObjectManager);
        final Rectangle left = new Rectangle(-20, -20, 2, GLGame.CAMERA_HEIGHT, vertexBufferObjectManager);
        final Rectangle right = new Rectangle(GLGame.CAMERA_WIDTH - 2+20, 20, 2, GLGame.CAMERA_HEIGHT, vertexBufferObjectManager);
        final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
        PhysicsFactory.createBoxBody(physicsWorld, ground, BodyDef.BodyType.StaticBody, wallFixtureDef);
        PhysicsFactory.createBoxBody(physicsWorld, roof, BodyDef.BodyType.StaticBody, wallFixtureDef);
        PhysicsFactory.createBoxBody(physicsWorld, left, BodyDef.BodyType.StaticBody, wallFixtureDef);
        PhysicsFactory.createBoxBody(physicsWorld, right, BodyDef.BodyType.StaticBody, wallFixtureDef);
        attachChild(ground);
        attachChild(roof);
        attachChild(left);
        attachChild(right);


        //flugnut
        flugnut.initForScene(GLGame.CAMERA_WIDTH/2, 100, vertexBufferObjectManager, physicsWorld);

        registerUpdateHandler(physicsWorld);

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
    }

//    public void destroyBodies() {
//        if(!world.isLocked()) {
//            if (jointsToDestroy != null && jointsToDestroy.size() > 0) {
//                for (Joint b : jointsToDestroy) {
//                    world.destroyJoint(b);
//                }
//                jointsToDestroy.clear();
//            }
//            if (bodiesToDestroy != null && bodiesToDestroy.size() > 0) {
//                for (Body b : bodiesToDestroy) {
//                    world.destroyBody(b);
//                }
//                bodiesToDestroy.clear();
//            }
//        }
//    }

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



    public boolean buildingsDead() {
//        for (int i = 0; i < buildings.size(); i++) {
//            if (buildings.get(i).health > 0) return false;
//        }
        return true;
    }

    private void updateBuildings(float deltaTime) {
//        for (Building b: buildings) {
//            b.update(deltaTime);
//        }
    }

    private void updateWaves(float deltaTime) {
        for (int i=0; i < waves.size(); i++) {
            Wave wave = waves.get(i);
            if (wave.nextStartTime > worldTime)
            {
                continue;
            }
            switch (wave.waveType) {
                case SIMPLE_MISSILE:
                    spawnNewMissiles(wave.quantity);
                    wave.nextStartTime += totalWaveTime; //run again after the last one runs
                    break;
                case HEAVY_MISSILE:
                    spawnNewHeavyMissiles(wave.quantity);
                    wave.nextStartTime += totalWaveTime; //run again after the last one runs
                    break;
                case FIRE:
                    break;
                default:
                    break;
            }
        }
    }

    private void updateMissiles(float deltaTime) {
//        if (bombs.size() > 0 && allMissilesDone()) {
//            bombs.clear();
//        }
//        if (bombs.size() > 0) {
//            for (int i = 0; i < bombs.size(); i++) {
//                Bomb b = bombs.get(i);
//                if (b.done)
//                {
//                    b.destroy();
//                    bombs.remove(i);
//                    i--;
//                }
//                if (b.health <= 0 && !b.done) {
//                    b.done = true;
//                }
//            }
//        }
    }

    private void updateMisc(float deltaTime) {
//        for (int i = 0; i < miscObjects.size(); i++) {
//            MiscObject mo = miscObjects.get(i);
//            if (mo.body.getPosition().x < -200) {
//                bodiesToDestroy.add(mo.body);
//                miscObjects.remove(i);
//                i--;
//                birdAdded = false;
//            }
//            else {
//                miscObjects.get(i).update(deltaTime);
//            }
//        }
    }

    private void updateEMPs(float deltaTime) {
//        for (int i = 0; i < empCircles.size(); i++) {
//            EMPCircle am = empCircles.get(i);
//
//            // anti-missile is done, remove it.
//            if (am.radius >= EMPCircle.EMP_MAX_RADIUS) {
//                am.image.getTexture().dispose();
//                bodiesToDestroy.add(am.body);
//                empCircles.remove(i);
//                i = i - 1;
//                continue;
//            }
//
//            // change anti-missile radius.
//            am.radius += EMPCircle.EMP_GROWTH_SPEED * deltaTime;
//        }
    }

    private void updateEMPSuperCircles(float deltaTime) {
//        for (int i = 0; i < empSuperCircles.size(); i++) {
//            EMPSuperCircle am = empSuperCircles.get(i);
//
//            // anti-missile is done, remove it.
//            if (am.radius >= EMPSuperCircle.EMP_MAX_RADIUS) {
//                am.image.getTexture().dispose();
//                empSuperCircles.remove(i);
//                i = i - 1;
//                continue;
//            }
//
//            // change anti-missile radius.
//            am.radius += EMPSuperCircle.EMP_GROWTH_SPEED * deltaTime;
//        }
    }

    private void updateEMPLaunchers(float deltaTime) {
//        for (int i = 0; i < empLaunchers.size(); i++) {
//            EMPLauncher empl = empLaunchers.get(i);
//            empl.update(deltaTime);
//            if (empl.done) {
//                empCircles.add(new EMPCircle(game, "emp1.png", empl.endx, empl.endy, this));
//                empLaunchers.remove(i);
//                i=i-1;
//                continue;
//            }
//        }
    }

    private void updateHorizEmps(float deltaTime) {
//        for (int i = 0; i < horizEmps.size(); i++) {
//            EMPHoriz he = horizEmps.get(i);
//
//            // anti-missile is done, remove it.
//            if (he.health == 0 || he.time > 10) {
//                he.image.getTexture().dispose();
//                horizEmps.remove(i);
//                i = i - 1;
//                continue;
//            }
//
//            // change anti-missile radius.
//            if (he.width < IScreen.glGraphics.getWidth() * 2)
//                he.width += EMPCircle.EMP_GROWTH_SPEED * 50 * deltaTime;
//            he.time += deltaTime;
//        }
    }

    private void updateZigZagEmps(float deltaTime) {
//        for (int i = 0; i < empZigZags.size(); i++) {
//            EMPZigZag he = empZigZags.get(i);
//            for (int j = 0; j < he.zigs.size(); j++) {
//                Zig zig = he.zigs.get(j);
//                // anti-missile is done, remove it.
//                if (zig.health == 0 || zig.time > 15) {
//                    he.zigs.remove(j);
//                    j = j - 1;
//                    continue;
//                }
//                zig.time += deltaTime;
//            }
//        }
    }

    private boolean allMissilesDone() {
//        for (int i = 0; i < bombs.size(); i++) {
//            if (!bombs.get(i).done)
//                return false;
//        }
        return true;
    }

    public void spawnNewMissiles(int missileToSpawn) {
        Random r = new Random();
//        for (int i = 0; i < missileToSpawn; i++) {
//            int missileStartX = r.nextInt(IScreen.glGraphics.getWidth());
//            int tbIndex = r.nextInt(buildings.size());
//            int count = 0;
//            while (buildings.get(tbIndex).health <= 0 && count < 20)
//            {
//                tbIndex = r.nextInt(buildings.size());
//                count++;
//            }
//            if (buildings.get(tbIndex).health <=0)
//            {
//                tbIndex = -1;
//                for (int j = 0; j< buildings.size(); j++) {
//                    Building building = buildings.get(j);
//                    if (building.health >= 0)
//                    {
//                        tbIndex = j;
//                        break;
//                    }
//                }
//            }
//            if (tbIndex != -1) {  //If it were -1, that means there are no buildings left.
//                Bomb b =  new SimpleBomb(game, "missile.png", missileStartX, world);
//                b.flugnutWorld = this;
//                bombs.add(b);
//            }
//        }
    }

    public void spawnNewHeavyMissiles(int missileToSpawn) {
//        Random r = new Random();
//        for (int i = 0; i < missileToSpawn; i++) {
//            int missileStartX = r.nextInt(IScreen.glGraphics.getWidth());
//            int tbIndex = r.nextInt(buildings.size());
//            int count = 0;
//            while (buildings.get(tbIndex).health <= 0 && count < 20)
//            {
//                tbIndex = r.nextInt(buildings.size());
//                count++;
//            }
//            if (buildings.get(tbIndex).health <=0)
//            {
//                tbIndex = -1;
//                for (int j = 0; j< buildings.size(); j++) {
//                    Building building = buildings.get(j);
//                    if (building.health >= 0)
//                    {
//                        tbIndex = j;
//                        break;
//                    }
//                }
//            }
//            if (tbIndex != -1) {  //If it were -1, that means there are no buildings left.
//                Bomb b =  new HeavyBomb(game, "missile.png", missileStartX, world);
//                b.flugnutWorld = this;
//                bombs.add(b);
//            }
//        }
    }

    public void destroy() {
//        flugnut.destroy();
//        for (Building b : buildings){
//            b.destroy();
//        }
//        for (MiscObject mo : miscObjects){
//            mo.destroy();
//        }
//        for(Bomb b : bombs) {
//            b.destroy();
//        }
//        for (EMP ec : empCircles) {
//            ec.destroy();
//        }
//        for (EMP ec : empSuperCircles) {
//            ec.destroy();
//        }
//        for (EMPLauncher el : empLaunchers) {
//            el.destroy();
//        }
//        for (EMP ec : horizEmps) {
//            ec.destroy();
//        }
//        for (EMPZigZag ez : empZigZags) {
//            ez.destroy();
//        }
//        FlugnutWorld.bodiesToDestroy = null;
    }


    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        final Sprite flugnutSprite = (Sprite) pTouchArea;
        switch(pSceneTouchEvent.getAction()) {
            case TouchEvent.ACTION_DOWN:
                /*
                 * If we have a active MouseJoint, we are just moving it around
                 * instead of creating a second one.
                 */
                if(this.mMouseJointActive == null) {
                    this.mMouseJointActive = this.createMouseJoint(flugnutSprite, pTouchAreaLocalX, pTouchAreaLocalY);
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
                if(this.mMouseJointActive != null) {
//                    final Vector2 vec = Vector2Pool.obtain(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
//                    if (flugnutSprite.contains(vec.x, vec.y)) {  //only destroy the joint if its a flick within the flugnut body.
//                        this.physicsWorld.destroyJoint(this.mMouseJointActive);
//                        this.mMouseJointActive = null;
//                    }
                }
                return true;
        }
        return false;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if(this.physicsWorld != null) {
            switch(pSceneTouchEvent.getAction()) {
                case TouchEvent.ACTION_DOWN:
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

    public MouseJoint createMouseJoint(final IAreaShape pFace, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        final Body body = (Body) pFace.getUserData();
        final MouseJointDef mouseJointDef = new MouseJointDef();

        final Vector2 localPoint = Vector2Pool.obtain((pTouchAreaLocalX - pFace.getWidth() * 0.5f) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, (pTouchAreaLocalY - pFace.getHeight() * 0.5f) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
        this.mGroundBody.setTransform(localPoint, 0);

        mouseJointDef.bodyA = this.mGroundBody;
        mouseJointDef.bodyB = body;
        mouseJointDef.dampingRatio = .99f;
        mouseJointDef.frequencyHz = 30;
        mouseJointDef.maxForce = (40.0f * body.getMass());
        mouseJointDef.collideConnected = true;

        mouseJointDef.target.set(body.getWorldPoint(localPoint));
        Vector2Pool.recycle(localPoint);

        return (MouseJoint) this.physicsWorld.createJoint(mouseJointDef);
    }
}
