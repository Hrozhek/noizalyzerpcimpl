package transport.websocket;

import config.ConnectionConfig;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;

@ClientEndpoint
public class WebSocketClient {

    private static final int DEFAULT_BUFFER_SIZE = 10 * 1024 * 1024;//10 mb

    private volatile Session session;

    public WebSocketClient(ConnectionConfig connectionConfig, String ws, int sensorId) {
        URI uri = createUri(connectionConfig, ws, sensorId);
        System.out.println("ws ur: " + uri);
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.setDefaultMaxBinaryMessageBufferSize(DEFAULT_BUFFER_SIZE);
            container.connectToServer(this, uri);
        } catch (DeploymentException | IOException e) {
            System.out.println("ws exc while connecting: " + e);//todo
        }
    }

    private URI createUri(ConnectionConfig connectionConfig, String ws, int sensorId) {
        String uriString = String.format("ws://%s:%d/%s/%s/%s", connectionConfig.getServer(),
                connectionConfig.getPort(), connectionConfig.getEndpoint(), ws, sensorId);
        return URI.create(uriString);
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnError
    public void onError(Throwable throwable) {
        System.out.println("ws err: " + throwable);//todo
    }

    @OnClose
    public void onClose() {
        System.out.println("on close");//todo
    }

    public void send(byte[] bytes) {
        if (!isActive()) {
            return;
        }
        try {
            session.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //todo
    }

    public boolean isActive() {
        return session != null && session.isOpen();
    }
}
