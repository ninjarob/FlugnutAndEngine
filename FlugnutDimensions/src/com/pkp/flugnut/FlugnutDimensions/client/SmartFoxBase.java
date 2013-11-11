package com.pkp.flugnut.FlugnutDimensions.client;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.game.ConnectionStatus;
import com.pkp.flugnut.FlugnutDimensions.model.AsteroidArea;
import com.pkp.flugnut.FlugnutDimensions.model.StationaryBody;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
import com.pkp.flugnut.FlugnutDimensions.utils.GenerateWorldObjects;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import org.andengine.entity.primitive.Vector2;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.entities.Room;
import sfs2x.client.entities.User;
import sfs2x.client.requests.ExtensionRequest;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/1/13
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmartFoxBase implements IEventListener{

    private final String TAG = this.getClass().getSimpleName();
//    private final static String TAB_TAG_CHAT = "tChat";
//    private final static String TAB_TAG_USERS = "tUsers";

    private final static boolean DEBUG_SFS = true;
    private final static boolean VERBOSE_MODE = true;

    private final static String DEFAULT_SERVER_ADDRESS = "192.168.1.105";
    private final static String DEFAULT_SERVER_PORT = "9933";

//    private final static int COLOR_GREEN = Color.parseColor("#99FF99");
//    private final static int COLOR_BLUE = Color.parseColor("#99CCFF");
//    private final static int COLOR_GRAY = Color.parseColor("#cccccc");
//    private final static int COLOR_RED = Color.parseColor("#FF0000");
//    private final static int COLOR_ORANGE = Color.parseColor("#f4aa0b");

    ConnectionStatus currentStatus;

    SmartFox sfsClient;

    View layoutConnector, layoutLogin, layoutChat;
    TextView labelStatus;
    ArrayAdapter<String> adapterUsers;
    MessagesAdapter adapterMessages;

    GLGame game;
    Room room;

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

        if (VERBOSE_MODE)
            Log.v(TAG, "SmartFox created:" + sfsClient.isConnected() + " BlueBox enabled="
                    + sfsClient.useBlueBox());
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
                    receiveReadyGame(event);
                }
                if (event.getType().equalsIgnoreCase(SFSEvent.CONNECTION)) {
                    if (event.getArguments().get("success").equals(true)) {
                        setStatus(ConnectionStatus.Status.CONNECTED, sfsClient.getConnectionMode());
                    } else {
                        setStatus(ConnectionStatus.Status.CONNECTION_ERROR);
                    }
                } else if (event.getType().equalsIgnoreCase(SFSEvent.CONNECTION_LOST)) {
                    disconnect();
                    setStatus(ConnectionStatus.Status.CONNECTION_LOST);
                } else if (event.getType().equalsIgnoreCase(SFSEvent.LOGIN)) {
                    setStatus(ConnectionStatus.Status.LOGGED, sfsClient.getCurrentZone());
                } else if (event.getType().equalsIgnoreCase(SFSEvent.ROOM_JOIN)) {
                    room = (Room) event.getArguments().get("room");
                    setStatus(ConnectionStatus.Status.IN_A_ROOM, sfsClient.getLastJoinedRoom().getName());

                }// When a user enter the room the user list is updated
                else if (event.getType().equals(SFSEvent.USER_ENTER_ROOM)) {
                    final User user = (User) event.getArguments().get("user");
                    if (VERBOSE_MODE) Log.v(TAG, "User '" + user.getName() + "' joined the room");
                    adapterUsers.add(user.getName());
                    //updateUsersTabLabel();
                    adapterMessages.add(new ChatMessage("User '" + user.getName() + "' joined the room"));
                }
                // When a user leave the room the user list is updated
                else if (event.getType().equals(SFSEvent.USER_EXIT_ROOM)) {
                    final User user = (User) event.getArguments().get("user");
                    if (VERBOSE_MODE) Log.v(TAG, "User '" + user.getName() + "' left the room");
                    adapterUsers.remove(user.getName());
                   // updateUsersTabLabel();
                    adapterMessages.add(new ChatMessage("User '" + user.getName() + "' left the room"));
                }
                // When public message is received it's added to the chat
                // history
                else if (event.getType().equals(SFSEvent.PUBLIC_MESSAGE)) {
                    ChatMessage message = new ChatMessage();
                    User sender = (User) event.getArguments().get("sender");
                    message.setUsername(sender.getName());
                    message.setMessage(event.getArguments().get("message").toString());
                    message.setDate(new Date());
                    // If my id and the sender id are different is a incoming
                    // message
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
        // if the user have entered port number it uses it...
        if (serverPort.length() > 0) {
            int serverPortValue = Integer.parseInt(serverPort);
            // tries to connect to the server
            // connectToServer(serverIP, serverPortValue);
            sfsClient.connect(serverIP, serverPortValue);
        }
        // ...otherwise uses the default port number
        else {
            // tries to connect to the server
            // connectToServer(serverIP);
            sfsClient.connect(serverIP);
        }
    }

    public void login() {
        String userNick = "Joe";
        String zoneName = "FlugnutDimensions";
        if (VERBOSE_MODE) Log.v(TAG, "Login as '" + userNick + "' into " + zoneName);
        LoginRequest loginRequest = new LoginRequest(userNick, "", zoneName);
        sfsClient.send(loginRequest);
    }

    public void joinRoom(String roomName) {
        sfsClient.send(new JoinRoomRequest(roomName));
    }

    public void disconnect() {
        if (VERBOSE_MODE) Log.v(TAG, "Disconnecting");
        if (sfsClient.isConnected()) {
            if (VERBOSE_MODE) Log.v(TAG, "Disconnect: Disconnecting client");
            sfsClient.disconnect();
            if (VERBOSE_MODE) Log.v(TAG, "Disconnect: Disconnected ? " + !sfsClient.isConnected());
            // initSmartFox();
        }
    }

    private void setStatus(ConnectionStatus.Status status, String... params) {
//        if (status == currentStatus.getStatus()) {
//            return;
//        }

        if (VERBOSE_MODE) Log.v(TAG, "New status= " + status);
        currentStatus.setStatus(status);
    }

    public ConnectionStatus getStatus() {
        return currentStatus;
    }

//    private void setStatusMessage(final String message, final int color) {
//        labelStatus.setText(message);
//        if (color != 0) {
//            labelStatus.setTextColor(color);
//        }
//    }

//    private void showLayout(final View layoutToShow) {
//        game.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                // Show the layout selected and hide the others
//                for (View layout : new View[]{layoutChat, layoutConnector, layoutLogin}) {
//                    if (layoutToShow == layout) {
//                        layout.setVisibility(View.VISIBLE);
//                    } else {
//                        layout.setVisibility(View.GONE);
//                    }
//                }
//            }
//        });
//    }

    public void receiveReadyGame(BaseEvent event) {
        SFSObject dataHolder = (SFSObject)(event.getArguments().get("params"));
        Integer systemRadius = dataHolder.getInt("rad");
        ISFSArray sbds = dataHolder.getSFSArray("sbds");
        ISFSArray abs = dataHolder.getSFSArray("abs");
        List<StationaryBody> stationaryBodies = new ArrayList<StationaryBody>();
        for (int i = 0; i < sbds.size(); i++) {
            ISFSObject sfsObj = sbds.getSFSObject(i);
            Vector2 loc = new Vector2(sfsObj.getFloat("x"), sfsObj.getFloat("y"));
            StationaryBody sb = new StationaryBody(loc, sfsObj.getInt("bt"), sfsObj.getInt("rad"));
            stationaryBodies.add(sb);
        }
        List<AsteroidArea> asteroidAreas = new ArrayList<AsteroidArea>();
        for (int i = 0; i < abs.size(); i++) {
            ISFSObject sfsObj = abs.getSFSObject(i);
            float x1 = sfsObj.getFloat("x1");
            float y1 = sfsObj.getFloat("y1");
            float x2 = sfsObj.getFloat("x2");
            float y2 = sfsObj.getFloat("y2");
            int numberOfAsteroids = sfsObj.getInt("num");

            AsteroidArea ab = new AsteroidArea(new Vector2(x1, y1), new Vector2(x2,y2), numberOfAsteroids);
            asteroidAreas.add(ab);
        }

        GameScene gs = new GameScene(game, GenerateWorldObjects.generateTutorial1(asteroidAreas, stationaryBodies, systemRadius), this);
        game.setNewScene(gs);
    }

    public void sendReadyGame() {
        SFSObject obj = new SFSObject();
        obj.putInt("actionId", 3);
        if (room!=null) {
            ExtensionRequest r = new ExtensionRequest("gameSetup", obj);
            sfsClient.send(r);
        }
    }
}
