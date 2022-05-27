package transport;

import config.ConnectionConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class ConnectionClient {

    private final ConnectionConfig config;

    private volatile boolean connected;

    private final HttpClient client = HttpClient.newHttpClient();

    public ConnectionClient(ConnectionConfig config) {
        this.config = config;
    }

    public UUID initConnection() {//todo return id
        if (false) {
            //todo throw or ignore if connected
        }
        HttpRequest request = initRequest("controller");
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);//todo
        }
        System.out.println("Controller registered! " + response + " body: " + response.body());
        connected = true;
        String uuid = response.body();
        uuid = uuid.substring(uuid.indexOf("\"") + 1, uuid.length() - 1);
        System.out.println("uuid: " + uuid);
        return UUID.fromString(uuid);
    }

    private HttpRequest initRequest(String path) {
        String uriString = String.format("http://%s:%d/%s/%s", config.getServer(), config.getPort(), config.getEndpoint(), path);
        URI uri = URI.create(uriString);
        return HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.noBody()).build();
    }

    public String getWsLink(UUID id) {//todo
        String path = String.format("controller/%s/file", id);
        HttpRequest request = initRequest(path);
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);//todo
        }
        System.out.println("Ws received " + response + " body: " + response.body());
        return response.body();
    }
}
