package com.pkp.flugnut.FlugnutDimensions.client;

import android.util.Log;
import com.badlogic.gdx.math.Vector2;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.ConnectionStatus;
import com.pkp.flugnut.FlugnutDimensions.game.ImageResourceCategory;
import com.pkp.flugnut.FlugnutDimensions.game.TextureType;
import com.pkp.flugnut.FlugnutDimensions.gameObject.*;
import com.pkp.flugnut.FlugnutDimensions.level.GameSceneInfo;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidInfo;
import com.pkp.flugnut.FlugnutDimensions.model.NPCInfo;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import com.pkp.flugnut.FlugnutDimensions.utils.GenerateWorldObjects;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.entities.Room;
import sfs2x.client.entities.User;
import sfs2x.client.requests.ExtensionRequest;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/1/13
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmartFoxBase implements IEventListener{

    private final String TAG = this.getClass().getSimpleName();

    private final static boolean DEBUG_SFS = true;
    private final static boolean VERBOSE_MODE = true;

    private final static String DEFAULT_SERVER_ADDRESS = "192.168.1.105";
    //private final static String DEFAULT_SERVER_PORT = "9933";
    private final static String DEFAULT_SERVER_PORT = "8082";

    private ConnectionStatus currentStatus;

    private SmartFox sfsClient;
    private MessagesAdapter adapterMessages;

    private GLGame game;
    private Room room;
    private GameSceneInfo gsi;

    public SmartFoxBase(GLGame game) {
        this.game = game;
        System.setProperty("java.net.preferIPv6Addresses", "false");
        initSmartFox();
        currentStatus=new ConnectionStatus();
    }

    private void initSmartFox() {

        // Instantiate SmartFox client
        sfsClient = new SmartFox(DEBUG_SFS);

        // Add event listeners
        sfsClient.addEventListener(SFSEvent.CONNECTION, this);
        sfsClient.addEventListener(SFSEvent.CONNECTION_LOST, this);
        sfsClient.addEventListener(SFSEvent.LOGIN, this);
        sfsClient.addEventListener(SFSEvent.LOGIN_ERROR, this);
        sfsClient.addEventListener(SFSEvent.ROOM_JOIN, this);
        sfsClient.addEventListener(SFSEvent.USER_ENTER_ROOM, this);
        sfsClient.addEventListener(SFSEvent.USER_EXIT_ROOM, this);
        sfsClient.addEventListener(SFSEvent.PUBLIC_MESSAGE, this);
        sfsClient.addEventListener(SFSEvent.EXTENSION_RESPONSE, this);
    }

    @Override
    public void dispatch(final BaseEvent event) throws SFSException {

        game.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (VERBOSE_MODE)
                    Log.v(TAG,
                            "Dispatching " + event.getType() + " (arguments="
                                    + event.getArguments() + ")");

                if (event.getType().equalsIgnoreCase(SFSEvent.EXTENSION_RESPONSE)) {
                    Log.v(TAG,"Dispatching " + event.getType() + " (arguments="+ event.getArguments() + ")");
                    String cmd = (String)(event.getArguments().get("cmd"));
                    if ("npcd".equals(cmd)) {
                        Log.d(TAG, "update npcs");
                        receiveNpcUpdate(event);
                    }
                    else if ("astd".equals(cmd)) {
                        Log.d(TAG, "update asteroids");
                        receiveAsteroidUpdate(event);
                    }
                    else if ("setup_game_for_client".equals(cmd)) {
                        receiveReadyGame(event);
                    }
                }
                if (event.getType().equalsIgnoreCase(SFSEvent.CONNECTION)) {
                    if (event.getArguments().get("success").equals(true)) {
                        setStatus(ConnectionStatus.Status.CONNECTED);
                    } else {
                        setStatus(ConnectionStatus.Status.CONNECTION_ERROR);
                    }
                } else if (event.getType().equalsIgnoreCase(SFSEvent.CONNECTION_LOST)) {
                    disconnect();
                    setStatus(ConnectionStatus.Status.CONNECTION_LOST);
                } else if (event.getType().equalsIgnoreCase(SFSEvent.LOGIN)) {
                    setStatus(ConnectionStatus.Status.LOGGED);
                } else if (event.getType().equalsIgnoreCase(SFSEvent.ROOM_JOIN)) {
                    room = (Room) event.getArguments().get("room");
                    setStatus(ConnectionStatus.Status.IN_A_ROOM);
                }
                else if (event.getType().equals(SFSEvent.USER_ENTER_ROOM)) {
                    final User user = (User) event.getArguments().get("user");
                    if (VERBOSE_MODE) Log.v(TAG, "User '" + user.getName() + "' joined the room");
                }
                else if (event.getType().equals(SFSEvent.USER_EXIT_ROOM)) {
                    final User user = (User) event.getArguments().get("user");
                    if (VERBOSE_MODE) Log.v(TAG, "User '" + user.getName() + "' left the room");
                }
                else if (event.getType().equals(SFSEvent.PUBLIC_MESSAGE)) {
                    ChatMessage message = new ChatMessage();
                    User sender = (User) event.getArguments().get("sender");
                    message.setUsername(sender.getName());
                    message.setMessage(event.getArguments().get("message").toString());
                    message.setDate(new Date());
                    message.setIncomingMessage(sender.getId() != sfsClient.getMySelf().getId());
                    adapterMessages.add(message);
                }
            }
        });

    }

    public void connect() {
        connect(DEFAULT_SERVER_ADDRESS, DEFAULT_SERVER_PORT);
    }

    public void connect(String serverIP, String serverPort) {
        if (serverPort.length() > 0) {
            int serverPortValue = Integer.parseInt(serverPort);
            sfsClient.setUseBlueBox(false);
            sfsClient.connect(serverIP, serverPortValue);
        }
        else {
            sfsClient.connect(serverIP);
        }
    }

    public void login() {
        String userNick = "Joe";
        String zoneName = "FlugnutDimensions";
        if (VERBOSE_MODE) {
            Log.v(TAG, "Login as '" + userNick + "' into " + zoneName);
        }
        LoginRequest loginRequest = new LoginRequest(userNick, "", zoneName);
        sfsClient.send(loginRequest);
    }

    public void joinRoom(String roomName) {
        sfsClient.send(new JoinRoomRequest(roomName));
    }

    public void updatePosition(Ship ship) {
        float x = ship.getSprite().getX();
        float y = ship.getSprite().getY();
        float angle = ship.getAngle();
        float thrust = ship.getThrustPercent();
        SFSObject obj = new SFSObject();
        obj.putFloat("x", x);
        obj.putFloat("y", y);
        obj.putFloat("a", angle);
        obj.putFloat("t", thrust);

        ExtensionRequest r = new ExtensionRequest("shipPosition", obj);
        sfsClient.send(r);
    }

    public void disconnect() {
        if (VERBOSE_MODE) Log.v(TAG, "Disconnecting");
        if (sfsClient.isConnected()) {
            if (VERBOSE_MODE) Log.v(TAG, "Disconnect: Disconnecting client");
            sfsClient.disconnect();
            if (VERBOSE_MODE) Log.v(TAG, "Disconnect: Disconnected ? " + !sfsClient.isConnected());
        }
    }

    private void setStatus(ConnectionStatus.Status status) {
        if (VERBOSE_MODE) {
            Log.v(TAG, "New status= " + status);
        }
        currentStatus.setStatus(status);
    }

    public ConnectionStatus getStatus() {
        return currentStatus;
    }

    public void receiveReadyGame(BaseEvent event) {

        GenerateWorldObjects gwo = new GenerateWorldObjects(game);
        gsi = gwo.generateSolSystem((SFSObject)(event.getArguments().get("params")));
        GameScene gs = new GameScene(game, gsi, this);
        game.setNewScene(gs);
    }

    public void receiveNpcUpdate(BaseEvent event) {
        ISFSArray positionData = ((ISFSObject)(event.getArguments().get("params"))).getSFSArray("positionData");
        if (positionData != null) {
            for (int i = 0; i < positionData.size(); i++) {
                ISFSObject positionObj = positionData.getSFSObject(i);
                Integer id = positionObj.getInt("nid");
                Boolean stopTrack = positionObj.containsKey("stop");
                if (stopTrack) {
                    gsi.removeNpcInfo(id);
                }
                else {
                    NPCInfo npcInfo = gsi.getNpcInfo(id);
                    Vector2 pos = new Vector2(positionObj.getFloat("x"), positionObj.getFloat("y"));
                    if (null == npcInfo) { //server has determined we are to start tracking it.
                        String name = positionObj.getUtfString("name");
                        npcInfo = new NPCInfo(id, name, pos);
                        Ship npcShip = new GawainShip(game, gsi.getGtamMap().get(ImageResourceCategory.GAWAIN).getTextureInfoHolder(TextureType.GAWAIN));
                        npcShip.initResources(gsi.getAtlasMap().get(ImageResourceCategory.GAWAIN));
                        npcShip.initSprites(gsi.getVertexBufferObjectManager());
                        npcShip.setStartPosition(pos);
                        GameScene scene = ((GameScene)(game.getCurrentScene()));
                        npcShip.setScene(scene);
                        scene.initNewObjectForScene(npcShip);
                        npcInfo.setShip(npcShip);
                        gsi.addNpcInfo(npcInfo);
                    }
                    else {
                        float thrust = positionObj.getFloat("t");
                        float angle = positionObj.getFloat("a");
                        npcInfo.setPos(pos);
                        int destIndex = npcInfo.getShip().getDestIndex(angle);
                        npcInfo.getShip().rotateShip(destIndex);
                    }
                }
            }
        }
    }

    public void receiveAsteroidUpdate(BaseEvent event) {
        ISFSArray positionData = ((ISFSObject)(event.getArguments().get("params"))).getSFSArray("apd");
        if (positionData != null) {
            for (int i = 0; i < positionData.size(); i++) {
                ISFSObject positionObj = positionData.getSFSObject(i);
                Integer id = positionObj.getInt("id");
                Boolean asteroidDestroyed = positionObj.containsKey("des");
                if (asteroidDestroyed) {
                    gsi.removeAsteroidInfo(id);
                }
                else {
                    AsteroidInfo asteroidInfo = gsi.getAsteroidInfo(id);
                    Vector2 pos = new Vector2(positionObj.getFloat("x"), positionObj.getFloat("y"));
                    Vector2 vel = new Vector2(positionObj.getFloat("vx"), positionObj.getFloat("vy"));
                    Integer hp = positionObj.getInt("hp");
                    if (null == asteroidInfo) { //server has determined we are to start tracking it.
                        Integer type = positionObj.getInt("t");
                        asteroidInfo = new AsteroidInfo(id, pos, vel, hp, type);
                        Asteroid asteroid;
                        switch (type) {
                            case 1:
                                asteroid = new Asteroid1(game,gsi.getGtamMap().get(ImageResourceCategory.ANIMATED_ASTEROID1).getTextureInfoHolder(TextureType.ASTEROID1));
                                asteroid.initResources(gsi.getAtlasMap().get(ImageResourceCategory.ANIMATED_ASTEROID1));
                                break;
                            case 2:
                                asteroid = new LargeAsteroid1(game,gsi.getGtamMap().get(ImageResourceCategory.NON_ANIMATED_ASTEROIDS).getTextureInfoHolder(TextureType.LARGE_ASTEROID1));
                                asteroid.initResources(gsi.getAtlasMap().get(ImageResourceCategory.NON_ANIMATED_ASTEROIDS));
                                break;
                            default:
                                asteroid = new Asteroid1(game,gsi.getGtamMap().get(ImageResourceCategory.ANIMATED_ASTEROID1).getTextureInfoHolder(TextureType.ASTEROID1));
                                asteroid.initResources(gsi.getAtlasMap().get(ImageResourceCategory.ANIMATED_ASTEROID1));

                        }
                        asteroid.initResources(gsi.getAtlasMap().get(ImageResourceCategory.GAWAIN));
                        asteroid.initSprites(gsi.getVertexBufferObjectManager());
                        asteroid.setStartPosition(pos);
                        GameScene scene = ((GameScene)(game.getCurrentScene()));
                        asteroid.setScene(scene);
                        scene.initNewObjectForScene(asteroid);
                        asteroidInfo.setAsteroid(asteroid);
                        gsi.addAsteroidInfo(asteroidInfo);
                    }
                    else {
                        asteroidInfo.setPos(pos);
                        //asteroidInfo.getAsteroid().getBody()
                        asteroidInfo.setVel(vel);
                        //??asteroidInfo.getAsteroid().getSprite().setPosition();
                        asteroidInfo.getAsteroid().getBody().setLinearVelocity(vel.x, vel.y);
                    }
                }
            }
        }
    }

    public boolean sendReadyGame() {
        SFSObject obj = new SFSObject();
        obj.putInt("actionId", 3);
        if (room!=null) {
            ExtensionRequest r = new ExtensionRequest("gameSetup", obj);
            sfsClient.send(r);
            return true;
        }
        return false;
    }



    //************************************
    // GETTERS AND SETTERS
    //************************************

    public ConnectionStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(ConnectionStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public SmartFox getSfsClient() {
        return sfsClient;
    }

    public void setSfsClient(SmartFox sfsClient) {
        this.sfsClient = sfsClient;
    }

    public MessagesAdapter getAdapterMessages() {
        return adapterMessages;
    }

    public void setAdapterMessages(MessagesAdapter adapterMessages) {
        this.adapterMessages = adapterMessages;
    }

    public GLGame getGame() {
        return game;
    }

    public void setGame(GLGame game) {
        this.game = game;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public GameSceneInfo getGsi() {
        return gsi;
    }

    public void setGsi(GameSceneInfo gsi) {
        this.gsi = gsi;
    }
}
