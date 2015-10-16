package com.zuehlke.jasschallenge.client.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuehlke.jasschallenge.client.websocket.messages.Message;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@WebSocket
public class RemoteGameSocket extends GameSocket {

    private final CountDownLatch closeLatch = new CountDownLatch(1);

    public RemoteGameSocket(GameHandler handler) {
        super(handler);
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @OnWebSocketConnect
    public void onWebSocketConnect(Session session) {
        logger.trace("Got connect: {}", session);
        super.onConnect(new WebSocketResponseChannel(session));
    }

    @OnWebSocketClose
    public void onWebSocketClose(int statusCode, String reason) {
        super.onClose(statusCode, reason);
        closeLatch.countDown();
    }

    @OnWebSocketMessage
    public void onWebSocketMessage(String msg) throws IOException {
        logger.trace("Received message: {}", msg);
        final Message message = read(msg, Message.class);
        super.onMessage(message);
    }

    private static <T> T read(String msg, Class<T> valueType) throws IOException {
        return new ObjectMapper().readValue(msg, valueType);
    }

}
