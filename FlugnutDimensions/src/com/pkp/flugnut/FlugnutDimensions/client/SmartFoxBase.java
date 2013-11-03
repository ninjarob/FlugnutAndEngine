package com.pkp.flugnut.FlugnutDimensions.client;


import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.pkp.flugnut.FlugnutDimensions.screen.global.GameScene;
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

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import com.pkp.flugnut.FlugnutDimensions.GLGame;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/1/13
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmartFoxBase implements IEventListener{



    private final String TAG = this.getClass().getSimpleName();
    private final static String TAB_TAG_CHAT = "tChat";
    private final static String TAB_TAG_USERS = "tUsers";

    private final static boolean DEBUG_SFS = true;
    private final static boolean VERBOSE_MODE = true;

    private final static String DEFAULT_SERVER_ADDRESS = "192.168.1.105";
    private final static String DEFAULT_SERVER_PORT = "9933";

    private final static int COLOR_GREEN = Color.parseColor("#99FF99");
    private final static int COLOR_BLUE = Color.parseColor("#99CCFF");
    private final static int COLOR_GRAY = Color.parseColor("#cccccc");
    private final static int COLOR_RED = Color.parseColor("#FF0000");
    private final static int COLOR_ORANGE = Color.parseColor("#f4aa0b");

    public enum Status {
        DISCONNECTED, CONNECTED, CONNECTING, CONNECTION_ERROR, CONNECTION_LOST, LOGGED, IN_A_ROOM
    }

    Status currentStatus = null;

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
        //initUI();
        setStatus(Status.DISCONNECTED);
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
                    setAsteroids(event);
                }
                if (event.getType().equalsIgnoreCase(SFSEvent.CONNECTION)) {
                    if (event.getArguments().get("success").equals(true)) {
                        setStatus(Status.CONNECTED, sfsClient.getConnectionMode());
                        // Login as guest in current zone
                        //showLayout(layoutLogin);
                        // sfsClient.send(new LoginRequest("", "",
                        // getString(R.string.example_zone)));
                    } else {
                        setStatus(Status.CONNECTION_ERROR);
                        showLayout(layoutConnector);
                    }
                } else if (event.getType().equalsIgnoreCase(SFSEvent.CONNECTION_LOST)) {
                    setStatus(Status.CONNECTION_LOST);
                    disconnect();
                    //adapterMessages.clear();
                    //adapterUsers.clear();
                    //showLayout(layoutConnector);
                    //try {
                        // Show a dialog with the reason
//                        new AlertDialog.Builder(SimpleChatActivity.this)
//                                .setTitle(R.string.connection_lost)
//                                .setMessage(
//                                        getString(R.string.dialog_connection_lost_message, event
//                                                .getArguments().get("reason").toString()))
//                                .setPositiveButton(android.R.string.ok, null).show();
                    //} catch (Exception e) {
                    //    e.printStackTrace();
                    //}
                } else if (event.getType().equalsIgnoreCase(SFSEvent.LOGIN)) {
                    setStatus(Status.LOGGED, sfsClient.getCurrentZone());
                    sfsClient.send(new JoinRoomRequest("SolSystem"));
                } else if (event.getType().equalsIgnoreCase(SFSEvent.ROOM_JOIN)) {
                    setStatus(Status.IN_A_ROOM, sfsClient.getLastJoinedRoom().getName());
                    //showLayout(layoutChat);
                    room = (Room) event.getArguments().get("room");
//                    for (User user : room.getUserList()) {
//                        adapterUsers.add(user.getName());
//                        //updateUsersTabLabel();
//                    }
//                    adapterMessages.add(new ChatMessage("Room [" + room.getName() + "] joined"));


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

    public void disconnect() {
        if (VERBOSE_MODE) Log.v(TAG, "Disconnecting");
        if (sfsClient.isConnected()) {
            if (VERBOSE_MODE) Log.v(TAG, "Disconnect: Disconnecting client");
            sfsClient.disconnect();
            if (VERBOSE_MODE) Log.v(TAG, "Disconnect: Disconnected ? " + !sfsClient.isConnected());
            // initSmartFox();
        }
    }

    private void setStatus(Status status, String... params) {
        if (status == currentStatus) {
            // If there is no status change ignore it
            return;
        }

        if (VERBOSE_MODE) Log.v(TAG, "New status= " + status);
        currentStatus = status;
        final String message;
        final int messageColor;
        final boolean connectButtonEnabled;
        switch (status) {
            case CONNECTING:
                message = "Connecting";
                messageColor = COLOR_BLUE;
                connectButtonEnabled = false;
                break;
            case DISCONNECTED:
                message = "Disconnected";
                messageColor = COLOR_GRAY;
                connectButtonEnabled = true;
                break;
            case CONNECTION_ERROR:
                message = "Connection Error";
                messageColor = COLOR_RED;
                connectButtonEnabled = true;
                break;
            case CONNECTED:
                message = "Connected: " + params[0];
                messageColor = COLOR_GREEN;
                connectButtonEnabled = false;
                break;
            case CONNECTION_LOST:
                message = "Connection Lost";
                messageColor = COLOR_ORANGE;
                connectButtonEnabled = true;
                break;
            case LOGGED:
                message = "Logged In To '" + params[0] /*
																		 * zone name
																		 */
                        + "' zone";
                messageColor = COLOR_GREEN;
                connectButtonEnabled = false;
                break;
            case IN_A_ROOM:
                message = "Joined Room" + params[0] /* room name */
                        + "'";
                messageColor = COLOR_GREEN;
                connectButtonEnabled = false;
                break;
            default:
                connectButtonEnabled = true;
                messageColor = 0;
                message = null;
        }
//        game.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                setStatusMessage(message, messageColor);
//                buttonConnect.setEnabled(connectButtonEnabled);
//            }
//        });

    }

    public Status getStatus() {
        return currentStatus;
    }

    private void setStatusMessage(final String message, final int color) {
        labelStatus.setText(message);
        if (color != 0) {
            labelStatus.setTextColor(color);
        }
    }

    private void showLayout(final View layoutToShow) {
        game.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // Show the layout selected and hide the others
                for (View layout : new View[]{layoutChat, layoutConnector, layoutLogin}) {
                    if (layoutToShow == layout) {
                        layout.setVisibility(View.VISIBLE);
                    } else {
                        layout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private GameScene gameScene;
    public void setAsteroids(BaseEvent event) {
        SFSObject dataHolder = (SFSObject)(event.getArguments().get("params"));
        Integer anum = dataHolder.getInt("asteroids");
        gameScene.setAsteroidNumber(anum);
        game.setNewScene(gameScene);
    }

    public void getAsteroids(GameScene gameScene) {
        this.gameScene = gameScene;
        SFSObject obj = new SFSObject();
        obj.putInt("actionId", 3);
        if (room!=null) {
            ExtensionRequest r = new ExtensionRequest("position", obj);
            sfsClient.send(r);
        }
    }
}
