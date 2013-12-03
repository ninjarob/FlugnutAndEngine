package com.pkp.flugnut.FlugnutDimensionsTests;

import com.pkp.flugnut.FlugnutDimensions.GLGame;
import com.pkp.flugnut.FlugnutDimensions.client.MessagesAdapter;
import com.pkp.flugnut.FlugnutDimensions.client.SmartFoxBase;
import com.pkp.flugnut.FlugnutDimensions.game.ConnectionStatus;
import com.pkp.flugnut.FlugnutDimensions.level.GameSceneInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sfs2x.client.SmartFox;
import sfs2x.client.entities.Room;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/18/13
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class SmartFoxBaseTest {

    private SmartFoxBase sfb;

    @Mock
    private ConnectionStatus currentStatus;

    @Mock
    private SmartFox sfsClient;

    @Mock
    private MessagesAdapter adapterMessages;

    @Mock
    private GLGame game;

    @Mock
    private Room room;

    @Mock
    private GameSceneInfo gsi;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sfb = new SmartFoxBase(game);
        sfb.setCurrentStatus(currentStatus);
        sfb.setSfsClient(sfsClient);
        sfb.setAdapterMessages(adapterMessages);
        sfb.setGame(game);
        sfb.setRoom(room);
        sfb.setGsi(gsi);
    }


    @BeforeMethod
    public void setup() {

    }

    @Test
    public void testDispatch() throws Exception {

    }

    @Test
    public void testConnect() throws Exception {

    }

    @Test
    public void testLogin() throws Exception {

    }

    @Test
    public void testJoinRoom() throws Exception {

    }

    @Test
    public void testUpdatePosition() throws Exception {

    }

    @Test
    public void testDisconnect() throws Exception {

    }

    @Test
    public void testGetStatus() throws Exception {

    }

    @Test
    public void testReceiveReadyGame() throws Exception {

    }

    @Test
    public void testReceiveNpcUpdate() throws Exception {

    }

    @Test
    public void testReceiveAsteroidUpdate() throws Exception {

    }

    @Test
    public void testSendReadyGame() throws Exception {
        boolean sent = sfb.sendReadyGame();
        Assert.assertTrue(sent);
    }

    @Test
    public void testSendReadyGameNoRoom() throws Exception {
        sfb.setRoom(null);
        boolean sent = sfb.sendReadyGame();
        Assert.assertFalse(sent);
    }
}
