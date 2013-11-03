package com.pkp.flugnut.FlugnutDimensions.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.EditText;
import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.client.SmartFoxBase;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/1/13
 * Time: 8:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class DialogHandler {
    private GLGame game;

    public static Dialog onCreateDialog(final GLGame game, final int pID, String message) {
        switch(pID) {
            case 1:
                final EditText ipEditText = new EditText(game);
                ipEditText.setText("192.168.1.101");
                return new AlertDialog.Builder(game)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("Enter Server-IP ...")
                        .setCancelable(false)
                        .setView(ipEditText)
                        .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface pDialog, final int pWhich) {
                                //new ConnectToServerAsyncTask(game, mServerConnector, ipEditText.getText().toString()).execute("");
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface pDialog, final int pWhich) {
                                game.finish();
                            }
                        })
                        .create();
            case 2:
                return new AlertDialog.Builder(game)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("Be Server or Client ...")
                        .setCancelable(false)
                        .setPositiveButton("Client", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface pDialog, final int pWhich) {
                                game.showDialog(1);
                            }
                        })
                        .create();

            case 3:
                return new AlertDialog.Builder(game)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("Server Connection Status")
                        .setMessage(message)
                        .setCancelable(false)
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface pDialog, final int pWhich) {

                                }
                        })
                        .create();

            default:
                return null;//onCreateDialog(pID);
        }
    }
}
