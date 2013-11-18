package com.pkp.flugnut.FlugnutDimensions.utils;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.client.SmartFoxBase;
import com.pkp.flugnut.FlugnutDimensions.gameObject.GameObject;
import com.pkp.flugnut.FlugnutDimensions.gameObject.Ship;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.TimeUtils;

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
    private SmartFoxBase sfb;
    private float secondsElapsedforShipUpdate = 0;

    public GameUpdateHandler(GLGame game, List<GameObject> gameObjects, SmartFoxBase sfb)    {
        this.game = game;
        this.gameObjects = gameObjects;
        this.sfb = sfb;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        //update objects already there.
        for (GameObject o : gameObjects) {
            if (o instanceof Ship) {
                Ship ship = (Ship) o;
                Vector2 vel = ship.getBody().getLinearVelocity();
                updateShip(ship);
                secondsElapsedforShipUpdate+=pSecondsElapsed;
                if (secondsElapsedforShipUpdate > 0.3f && (vel.x > 0.01f || vel.y > 0.01f) || ship.isChangedDirForServer()) {
                    sfb.updatePosition(ship);
                    ship.setChangedDirForServer(false);
                    secondsElapsedforShipUpdate = 0;
                }
            }
        }

        //check for new objects to be added and objects that need to be removed.
        List<GameObject> objectsThatNeedAdding;
        List<GameObject> objectsThatNeedRemoving;

        //remove objects that need removing;

        //add objects that need adding


    }

    private int accel = 10;
    private TimeUtils tu;
    private void updateShip(Ship ship) {
        if (ship.getThrustPercent() > 0) {
            float angle = ship.getAngleFromIndex(((AnimatedSprite)ship.getSprite()).getCurrentTileIndex());
            Vector2 vel = ship.getBody().getLinearVelocity();
            double mag = Math.sqrt(Math.pow(vel.x, 2)+Math.pow(vel.y, 2));
            if (ship.getThrustPercent() > 0  && mag <= 250*ship.getThrustPercent()) {
                ship.getBody().applyForceToCenter((float)Math.cos(angle)*accel*ship.getThrustPercent(), -(float)Math.sin(angle)*accel*ship.getThrustPercent());
            }
        }
    }

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
