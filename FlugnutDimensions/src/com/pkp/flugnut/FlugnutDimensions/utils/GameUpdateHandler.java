package com.pkp.flugnut.FlugnutDimensions.utils;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.gameObject.GameObject;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 9/18/13
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameUpdateHandler implements IUpdateHandler {
    private GLGame game;
    private List<GameObject> gameObjects;
    private GameScene gameScene;

    public GameUpdateHandler(GLGame game, List<GameObject> gameObjects, GameScene gameScene)    {
        this.game = game;
        this.gameObjects = gameObjects;
        this.gameScene = gameScene;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        //update objects already there.
        for (GameObject o : gameObjects) {
//            if (o instanceof Flugnut) {
//                updateFlugnut(o);
//            }
        }

        //check for new objects to be added and objects that need to be removed.
        List<GameObject> objectsThatNeedAdding;
        List<GameObject> objectsThatNeedRemoving;

        //remove objects that need removing;

        //add objects that need adding


    }

//    private void updateFlugnut(GameObject o) {
//        Flugnut f = (Flugnut)o;
//        for (GameObject o2 : gameObjects) {
//            if (o2 instanceof BlockBuilding)
//            {
//                if (f.getSprite().collidesWith(o2.getSprite())) {
//                    try {
//                        SoundFactory.setAssetBasePath("mfx/");
//                        Sound explosion = SoundFactory.createSoundFromAsset(game.getSoundManager(), game, "explosion.ogg");
//                        Utilities.playSound(explosion);
//                    }
//                    catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//        //handle bounds
//        float spritex = f.getSprite().getX();
//        float lwx = f.getGameScene().getLeftWall().getX();
//        float rwx = f.getGameScene().getRightWall().getX();
////        if (spritex-lwx < 390 ||
////                rwx-spritex < 390) {
////            if (!f.getOutOfBounds()) {
////                f.setOutOfBounds(true);
////                gameScene.releaseMouseJoint(f.getFlugnutShield());
////            }
////        }
////        else {
////            f.setOutOfBounds(false);
////        }
//        if (spritex-lwx > 290 &&
//                rwx-spritex > 290) {
//            game.mCamera.setCenter(f.getSprite().getX(), game.mCamera.getCenterY());
//        }
//    }

    public GLGame getGame() {
        return game;
    }

    public void setGame(GLGame game) {
        this.game = game;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void addGameObject(int location, GameObject gameObject) {
        gameObjects.add(location, gameObject);
    }

    public void addGameObject(GameObject putAfter, GameObject gameObject) {
        gameObjects.add(gameObjects.indexOf(putAfter), gameObject);
    }

    @Override
    public void reset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
