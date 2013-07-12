package com.pkp.flugnut.FlugnutAndEngine.utils;

import com.pkp.flugnut.FlugnutAndEngine.model.level.GameSceneInfo;

public class GenerateWorldObjects {

    public static GameSceneInfo generateTutorial1() {
        GameSceneInfo gsi = new GameSceneInfo();
        gsi.setBgFileName("background.png");
        gsi.setLocked(false);
        gsi.setWeaponEnabled(false);
        return gsi;
    }


    //ENEMY WAVES
//    private static Wave[][] staticWaves1 = {
//            {new Wave(Wave.WaveType.SIMPLE_MISSILE, 50, 4)},
//            {new Wave(Wave.WaveType.SIMPLE_MISSILE, 50, 4)},
//            {new Wave(Wave.WaveType.SIMPLE_MISSILE, 50, 4)},
//            {new Wave(Wave.WaveType.SIMPLE_MISSILE, 50, 4)},
//
//            {new Wave(Wave.WaveType.SIMPLE_MISSILE, 5, 4),
//                    new Wave(Wave.WaveType.SIMPLE_MISSILE, 6, 1),
//                    new Wave(Wave.WaveType.SIMPLE_MISSILE, 16, 4),
//                    new Wave(Wave.WaveType.SIMPLE_MISSILE, 19, 2),
//                    new Wave(Wave.WaveType.SIMPLE_MISSILE, 25, 5),
//                    new Wave(Wave.WaveType.SIMPLE_MISSILE, 29, 5),
//                    new Wave(Wave.WaveType.SIMPLE_MISSILE, 35, 6)},
//
////											{new Wave(WaveType.SIMPLE_MISSILE, 5, 4),
////												new Wave(WaveType.HEAVY_MISSILE, 6, 1),
////												new Wave(WaveType.HEAVY_MISSILE, 16, 2),
////												new Wave(WaveType.HEAVY_MISSILE, 22, 3),
////												new Wave(WaveType.SIMPLE_MISSILE, 29, 6)},
//
//            {new Wave(Wave.WaveType.SIMPLE_MISSILE, 5, 4),
//                    new Wave(Wave.WaveType.SIMPLE_MISSILE, 6, 3),
//                    new Wave(Wave.WaveType.HEAVY_MISSILE, 16, 4),
//                    new Wave(Wave.WaveType.HEAVY_MISSILE, 19, 2),
//                    new Wave(Wave.WaveType.HOVER_CRAFT, 25, 1)},
//    };
//
//    public static List<Wave> generateWaves(int level) {
//        switch (level) {
//            case 1:
//                return new ArrayList<Wave>();
//            case 2:
//                return new ArrayList<Wave>();
//            case 3:
//                return new ArrayList<Wave>();
//            default:
//                return Arrays.asList(staticWaves1[level-1]);
//        }
//    }

//    public static List<Building> generateBuildings(GLGame game, FlugnutWorld flugnutWorld, int level)
//	{
//		List<Building> buildings;
//		switch (level) {
//            case 1:
//                buildings = generateTutorial1Buildings(game, flugnutWorld);
//                break;
//            case 2:
//                buildings = generateTutorial2Buildings(game, flugnutWorld);
//                break;
//            case 3:
//                buildings = generateTutorial3Buildings(game, flugnutWorld);
//                break;
//			case 4:
//				buildings = generatreFlugnutHomeLevelBuildings(game, flugnutWorld);
//				break;
//			case 5:
//				buildings = generatreFlugnutLabLevelBuildings(game, flugnutWorld);
//				break;
//			case 6:
//				buildings = generatreFlugnutHangarLevelBuildings(game, flugnutWorld);
//				break;
//			default:
//				buildings = generatreFlugnutHomeLevelBuildings(game, flugnutWorld);
//		}
//		return buildings;
//	}
//
//    public static List<Building> generateTutorial1Buildings(GLGame game, FlugnutWorld flugnutWorld) {
//        List<Building> buildings = new ArrayList<Building>();
//        Building birdCage = new BirdCage(game, flugnutWorld);
//        BirdCageDoor d = new BirdCageDoor(game, flugnutWorld, birdCage);
//        d.clickable = true;
//        //setup victory objects and conditions
//        List<Bird> birds = new ArrayList<Bird>();
//        float startx = 100f/Constants.V_SCALE;
//        float starty = 200f/Constants.V_SCALE;
//        float yinc = 30f/Constants.V_SCALE;
//        for (int i = 0; i < 5; i++) {
//            Bird b = new Bird(game, startx, starty+(yinc*i), flugnutWorld, birdCage);
//            b.pickupable = true;
//            b.clickable = true;
//            b.name = "Bird #"+i;
//            birds.add(b);
//        }
//        flugnutWorld.gameScreen.level.victoryConditions.add(new BirdCollectVC(birds));
//        birdCage.buildingPieces.addAll(birds);
//        birdCage.buildingPieces.add(d);
//        buildings.add(birdCage);
//        return buildings;
//    }
//
//    public static List<Building> generateTutorial2Buildings(GLGame game, FlugnutWorld flugnutWorld) {
//        List<Building> buildings = new ArrayList<Building>();
//        EmptyBuilding empty = new EmptyBuilding(game, flugnutWorld);
//        Tower d = new Tower(game, 50, 50, flugnutWorld, empty);
//        d.clickable = true;
//        d.pickupable = true;
//        flugnutWorld.gameScreen.level.victoryConditions.add(new TowerBuiltVC(d));
//
//        List<TowerBox> tbs = new ArrayList<TowerBox>();
//        float startx = 100f/Constants.V_SCALE;
//        float starty = 200f/Constants.V_SCALE;
//        float yinc = 30f/Constants.V_SCALE;
//
//        for (int i = 0; i < 2; i++) {
//            TowerBox b = new TowerBox(game, startx+(yinc*i), starty+(yinc*i), flugnutWorld, empty);
//            b.pickupable = true;
//            b.clickable = true;
//            b.name = "Tower Box #"+i;
//            tbs.add(b);
//        }
//
//        empty.buildingPieces.addAll(tbs);
//        empty.buildingPieces.add(d);
//        buildings.add(empty);
//        return buildings;
//    }
//
//    public static List<Building> generateTutorial3Buildings(GLGame game, FlugnutWorld flugnutWorld) {
//        List<Building> buildings = new ArrayList<Building>();
//        EmptyBuilding empty = new EmptyBuilding(game, flugnutWorld);
//        List<SolarPod> pods = new ArrayList<SolarPod>();
//        pods.add(new SolarPod(game, 100/Constants.V_SCALE, 300/Constants.V_SCALE, flugnutWorld, empty));
//        pods.add(new SolarPod(game, 150/Constants.V_SCALE, 300/Constants.V_SCALE, flugnutWorld, empty));
//        pods.add(new SolarPod(game, 200/Constants.V_SCALE, 300/Constants.V_SCALE, flugnutWorld, empty));
//        pods.add(new SolarPod(game, 250/Constants.V_SCALE, 300/Constants.V_SCALE, flugnutWorld, empty));
//        pods.add(new SolarPod(game, 300/Constants.V_SCALE, 300/Constants.V_SCALE, flugnutWorld, empty));
//        flugnutWorld.gameScreen.level.victoryConditions.add(new PodsRechargedVC(pods));
//
//        empty.buildingPieces.addAll(pods);
//        buildings.add(empty);
//        return buildings;
//    }
//
//    public static List<Building> generatreFlugnutHomeLevelBuildings(GLGame game, FlugnutWorld flugnutWorld) {
//        List<Building> buildings = new ArrayList<Building>();
//        List<BuildingPiece> buildingPieces = new ArrayList<BuildingPiece>();
//        Building flugnutHome = new FlugnutHome(game, flugnutWorld);
//        float startx = 80f;
//        buildingPieces.add(new CircleWindow(game, flugnutWorld, Constants.F_HOME_STARTX+startx, flugnutHome));
//        startx = 118f;
//        buildingPieces.add(new CircleWindow(game, flugnutWorld, Constants.F_HOME_STARTX+startx, flugnutHome));
//        Door door = new Door(game, flugnutWorld, flugnutHome);
//        buildingPieces.add(door);
//        startx = 76f;
//        float incx = 12f/Constants.V_SCALE;
//        for (int i = 0; i < 9; i++) {
//            buildingPieces.add(new RoofPiece(game, Constants.F_HOME_STARTX+startx+(incx*i), flugnutWorld, flugnutHome));
//        }
//
//        //setup victory objects and conditions
//        Flag flag = new Flag(game, flugnutWorld, flugnutHome);
//        flag.clickable = true;
//        startx = 40f/Constants.V_SCALE;
//        Pylon pylon1 = new Pylon(game, flugnutWorld, startx, flugnutHome);
//        pylon1.clickable = true;
//        startx = 300f/ Constants.V_SCALE;
//        Pylon pylon2 =  new Pylon(game, flugnutWorld, startx, flugnutHome);
//        pylon2.clickable = true;
//        flugnutWorld.gameScreen.level.victoryConditions.add(new FlugnutHomeVC(flag, pylon1, pylon2));
//        buildingPieces.add(flag);
//        buildingPieces.add(pylon1);
//        buildingPieces.add(pylon2);
//        flugnutHome.buildingPieces = buildingPieces;
//        buildings.add(flugnutHome);
//        return buildings;
//    }
//
//    public static List<Building> generatreFlugnutLabLevelBuildings(GLGame game, FlugnutWorld flugnutWorld) {
//        List<Building> buildings = new ArrayList<Building>();
//        List<BuildingPiece> buildingPieces = new ArrayList<BuildingPiece>();
//        Building flugnutLab = new FlugnutLab(game, flugnutWorld);
//        //setup victory objects and conditions
//        float startx = 90f/Constants.V_SCALE;
//        Flag flag1 = new Flag(game, startx, Constants.F_LAB_FLAG1_Y_OFFSET, flugnutWorld, flugnutLab);
//        startx = 210f/Constants.V_SCALE;
//        Flag flag2 = new Flag(game, startx, Constants.F_LAB_FLAG2_Y_OFFSET, flugnutWorld, flugnutLab);
//        startx = 40f/Constants.V_SCALE;
//        flag1.clickable = true;
//        flag2.clickable = true;
//        float starty = flugnutLab.body.getPosition().y - flugnutLab.height/2 + Constants.F_HOME_PYLON_Y_OFFSET-10;
//        Pylon pylon1 = new Pylon(game, flugnutWorld, startx, starty, flugnutLab);
//        startx = 260/Constants.V_SCALE;
//        Pylon pylon2 =  new Pylon(game, flugnutWorld, startx, starty, flugnutLab);
//        pylon1.onFire = true;
//        pylon1.clickable = true;
//        pylon2.clickable = true;
//        startx = 300f/Constants.V_SCALE;
//        Well well = new Well(game, startx, flugnutWorld, flugnutLab);
//        well.clickable = true;
//        flugnutWorld.gameScreen.level.victoryConditions.add(new FlugnutLabVC(flag1, flag2, pylon1, pylon2, well));
//        buildingPieces.add(flag1);
//        buildingPieces.add(flag2);
//        buildingPieces.add(pylon1);
//        buildingPieces.add(pylon2);
//        buildingPieces.add(well);
//        flugnutLab.buildingPieces = buildingPieces;
//        buildings.add(flugnutLab);
//        return buildings;
//    }
//
//    public static List<Building> generatreFlugnutHangarLevelBuildings(GLGame game, FlugnutWorld flugnutWorld) {
//        List<Building> buildings = new ArrayList<Building>();
//        List<BuildingPiece> buildingPieces = new ArrayList<BuildingPiece>();
//        Building flugnutHangar = new FlugnutHangar(game, flugnutWorld);
//        List<Pylon> pylons = new ArrayList<Pylon>();
//        //setup victory objects and conditions
//        float startx = 53f/Constants.V_SCALE;
//        float starty = 50f/Constants.V_SCALE;
//        Pylon pylon1 =   new Pylon(game, flugnutWorld, startx, starty, (float)(Math.PI / 2.1), flugnutHangar);
//        startx = 65f/Constants.V_SCALE;
//        starty = 96f/Constants.V_SCALE;
//        Pylon pylon2 =  new Pylon(game, flugnutWorld, startx, starty, (float)(Math.PI / 3), flugnutHangar);
//        startx = 95f/Constants.V_SCALE;
//        starty = 135f/Constants.V_SCALE;
//        Pylon pylon3 =   new Pylon(game, flugnutWorld, startx, starty, (float)(Math.PI / 5), flugnutHangar);
//        startx = 137f/Constants.V_SCALE;
//        starty = 162f/Constants.V_SCALE;
//        Pylon pylon4 =  new Pylon(game, flugnutWorld, startx, starty, (float)(Math.PI / 8), flugnutHangar);
//        startx = 222f/Constants.V_SCALE;
//        starty = 162f/Constants.V_SCALE;
//        Pylon pylon5 =   new Pylon(game, flugnutWorld, startx, starty, -(float)(Math.PI / 8), flugnutHangar);
//        startx = 265f/Constants.V_SCALE;
//        starty = 135f/Constants.V_SCALE;
//        Pylon pylon6 =  new Pylon(game, flugnutWorld, startx, starty, -(float)(Math.PI / 5), flugnutHangar);
//        startx = 293f/Constants.V_SCALE;
//        starty = 96f/Constants.V_SCALE;
//        Pylon pylon7 =   new Pylon(game, flugnutWorld, startx, starty, -(float)(Math.PI / 3), flugnutHangar);
//        startx = 305f/Constants.V_SCALE;
//        starty = 50f/Constants.V_SCALE;
//        Pylon pylon8 =  new Pylon(game, flugnutWorld, startx, starty, -(float)(Math.PI / 2.1), flugnutHangar);
//        pylons.add(pylon1);
//        pylons.add(pylon2);
//        pylons.add(pylon3);
//        pylons.add(pylon4);
//        pylons.add(pylon5);
//        pylons.add(pylon6);
//        pylons.add(pylon7);
//        pylons.add(pylon8);
//        flugnutWorld.gameScreen.level.victoryConditions.add(new FlugnutHangarVC(pylons));
//        buildingPieces.addAll(pylons);
//        flugnutHangar.buildingPieces = buildingPieces;
//        buildings.add(flugnutHangar);
//        return buildings;
//    }
}
