package com.pkp.flugnut.FlugnutDimensions.model;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 12/7/13
 * Time: 9:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class GOPathFinder extends GOInfo implements PathFinder {

    private List<PathBehavior> pathBehaviors;

    public GOPathFinder(int id, Vector2 pos, float velMag, int type, String path) {
        super(id, pos, velMag, type);
        parsePath(path);
    }

    public void parsePath(String path) {
        //cco(x,y)
        //ccco(x,y)
        pathBehaviors = new ArrayList<PathBehavior>();
        String[] pathParts = path.split(";");
        for (String part : pathParts) {
            if (part.contains("cco")) {
                pathBehaviors.add(new OrbitalPath());
            }
        }
    }

    public PathBehavior getCurrentPathBehavior() {
        return pathBehaviors.get(0);
    }
}
