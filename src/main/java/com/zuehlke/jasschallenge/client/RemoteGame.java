package com.zuehlke.jasschallenge.client;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.websocket.GameHandler;
import com.zuehlke.jasschallenge.client.websocket.RemoteGameSocket;
import com.zuehlke.jasschallenge.messages.type.SessionType;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class RemoteGame implements Game {

    private static final Logger logger = LoggerFactory.getLogger(RemoteGame.class);
    private static final int CLOSE_TIMEOUT_MIN = 30;
    private final Player player;
    private final String targetUrl;
    private final SessionType sessionType;

    public RemoteGame(String targetUrl, Player player, SessionType sessionType) {
        this.targetUrl = targetUrl;
        this.player = player;
        this.sessionType = sessionType;
    }

    @Override
    public void start() throws Exception {
        final WebSocketClient client = new WebSocketClient();
        try {
            RemoteGameSocket socket = new RemoteGameSocket(new GameHandler(player, sessionType));
            client.start();

            URI uri = new URI(targetUrl);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, uri, request);
            logger.debug("Connecting to: {}", uri);
            socket.awaitClose(CLOSE_TIMEOUT_MIN, TimeUnit.MINUTES);
        } finally {
            client.stop();
        }
    }

}
