package transport.websocket;

import javax.websocket.ClientEndpoint;

@ClientEndpoint
public class WebSocketClient {

    public void send(byte[] bytes) {
        //todo
    }

    public boolean isActive() {
        return true;
    }
}
