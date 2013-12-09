package com.pkp.flugnut.FlugnutDimensions.model;

import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.gameObject.npc.Asteroid;
import com.pkp.flugnut.FlugnutDimensions.level.GameSceneInfo;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import com.smartfoxserver.v2.entities.data.ISFSObject;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/13/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsteroidInfo extends GOPathFinder {

    private Asteroid asteroid;
    private int hp;

    public AsteroidInfo(int id, Vector2 pos,  float velMag, int hp, int type, String path) {
        super(id, pos, velMag, type, path);
        this.hp = hp;
    }

    public Asteroid getAsteroid() {
        return asteroid;
    }

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void updateAsteroid(ISFSObject positionObj) {
        Vector2 pos = new Vector2(positionObj.getFloat("x"), positionObj.getFloat("y"));
        Integer hp = positionObj.getInt("hp");
        setPos(pos);
        setHp(hp);
        PathBehavior pb = getCurrentPathBehavior();
        pb.pathUpdate(this, pos);
    }

    public void removeAsteroid(GameScene scene, GameSceneInfo gsi) {
        PathBehavior pb = getCurrentPathBehavior();
        pb.clean();
        scene.getGameObjects().remove(this);
        gsi.removeAsteroidInfo(this.getId());
    }

}
