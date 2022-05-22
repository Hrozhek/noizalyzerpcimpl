package config.sensor;

import config.TimeoutConfig;
import sensor.SensorType;

public abstract class AbstractSensorConfig {

    protected final SensorType sensorType;
    protected final TimeoutConfig readDuration;

    public SensorType getSensorType() {
        return sensorType;
    }

    public TimeoutConfig getReadDuration() {
        return readDuration;
    }

    protected AbstractSensorConfig(SensorType sensorType, TimeoutConfig readDuration) {
        this.sensorType = sensorType;
        this.readDuration = readDuration;
    }
}
