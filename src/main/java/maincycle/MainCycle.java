package maincycle;

import config.ApplicationConfig;
import config.TimeoutConfig;
import config.sensor.AbstractSensorConfig;
import config.sensor.MicrophoneSensorConfig;
import sensor.AbstractSensor;
import sensor.Microphone;
import transport.ConnectionClient;
import transport.websocket.WebSocketClient;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MainCycle implements Runnable {

    private static volatile MainCycle INSTANCE;

    private static int SENSOR_ID = 0;

    private final Duration writeTimeout;
    private final TimeoutConfig updateRate;
    private final ConnectionClient connectionClient;
    private final UUID id;
    private final List<AbstractSensor> sensors = new ArrayList<>();
    private final Map<Integer, WebSocketClient> webSocketClients = new HashMap<>();

    private Instant lastWritten;
    private ExecutorService executor;

    private MainCycle(ApplicationConfig config) {
        writeTimeout = initWriteTimeout(config.getWriteTimeout());
        this.updateRate = config.getUpdateRate();
        connectionClient = new ConnectionClient(config.getConnection());
        id = connectionClient.initConnection();
        config.getSensors().forEach(this::initSensor);
        lastWritten = Instant.now().minus(writeTimeout);//allow first iteration in any case
    }

    private Duration initWriteTimeout(TimeoutConfig config) {
        return Duration.of(config.getTime(), config.getTimeUnit().toChronoUnit());
    }

    private void initSensor(AbstractSensorConfig sensorConfig) {
        AbstractSensor sensor;
        int id = SENSOR_ID++;
        switch (sensorConfig.getSensorType()) {
            case MICROPHONE -> sensor = new Microphone(id, (MicrophoneSensorConfig) sensorConfig);
            default -> throw new IllegalStateException("Unexpected value: " + sensorConfig.getSensorType());//todo
        }
        sensor.init();
        sensors.add(sensor);
    }

    public void start() {
        initExecutor(updateRate);
    }

    private ExecutorService initExecutor(TimeoutConfig config) {
        if (executor != null) {
            //todo
        }
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this, 0, config.getTime(), config.getTimeUnit());
        return executorService;
    }

    public void stop() {
        executor.shutdownNow();
    }

    @Override
    public void run() {
        //check server's messages like ping
        if (Instant.now().isAfter(lastWritten.plus(writeTimeout))) {
            doWebSocketRoutine();
            lastWritten = Instant.now();
        }

    }

    private void doWebSocketRoutine() {
        for (AbstractSensor sensor : sensors) {
            WebSocketClient client = webSocketClients.get(sensor.getId());
            if (client == null || !client.isActive()) {
                connectionClient.getWsLink();//todo init
            }
            byte[] read = sensor.read();
            //todo log
            client.send(read);
        }

    }

    public static MainCycle getInstance(ApplicationConfig config) {
        synchronized (MainCycle.class) {
            if (INSTANCE == null) {
                synchronized (MainCycle.class) {
                    INSTANCE = new MainCycle(config);
                }
            }
            return INSTANCE;
        }
    }
}
