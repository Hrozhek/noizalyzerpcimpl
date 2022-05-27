package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectionConfig {

    private static final class Token {

        public static final String SERVER = "server_url";
        public static final String PORT = "server_port";
        public static final String ENDPOINT = "endpoint";


    }

    private final String server;
    private final int port;
    private final String endpoint;

    @JsonCreator
    public ConnectionConfig(@JsonProperty(Token.SERVER) String server,
                            @JsonProperty(Token.PORT) int port,
                            @JsonProperty(Token.ENDPOINT) String endpoint) {
        this.server = server;
        this.port = port;
        this.endpoint = endpoint;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
