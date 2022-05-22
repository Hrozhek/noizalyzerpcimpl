package maincycle;

import config.ApplicationConfig;
import config.sensor.AbstractSensorConfig;
import sensor.AbstractSensor;
import sensor.Microphone;
import transport.ConnectionClient;
import transport.websocket.WebSocketClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainCycle {

    private static int SENSOR_ID = 0;

    private final ApplicationConfig config;
    private final ConnectionClient connectionClient;
    private final UUID id;
    private final List<AbstractSensor> sensors = new ArrayList<>();

    private volatile WebSocketClient webSocketClient;

    public MainCycle(ApplicationConfig config) {
        this.config = config;
        connectionClient = new ConnectionClient(config.getConnection());
        id = connectionClient.initConnection();
        config.getSensors().forEach(this::initSensor);
    }

    private void initSensor(AbstractSensorConfig sensorConfig) {
        int id = SENSOR_ID++;
        switch (sensorConfig.getSensorType()) {
            case MICROPHONE -> new Microphone();
        }
    }

    public void start() {
        //check server's messages like ping
        //check write timeout
        //sleep or doWebSocketRoutine
    }

    private void doWebSocketRoutine() {
        if (connectionClient == null /*|| !client.isActive()*/) {

        }
    }
}
