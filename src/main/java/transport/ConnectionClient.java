package transport;

import config.ConnectionConfig;

import java.util.UUID;

public class ConnectionClient {

    private final ConnectionConfig config;

    private volatile boolean connected;

    public ConnectionClient(ConnectionConfig config) {
        this.config = config;
    }

    public UUID initConnection() {//todo return id
        if (false) {
            //todo throw or ignore if connected
        }
        //todo
        connected = true;
        return null;
    }

    public void getWsLink() {//todo

    }
}
