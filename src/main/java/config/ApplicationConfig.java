package config;

import config.sensor.AbstractSensorConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApplicationConfig {

    private final TimeoutConfig writeTimeoutConfig;
    private final TimeoutConfig updateRate;
    private final ConnectionConfig connectionConfig;
    private final List<AbstractSensorConfig> sensorsConfig;

    public ApplicationConfig(TimeoutConfig writeTimeoutConfig, TimeoutConfig updateRate,
                             ConnectionConfig connectionConfig, List<AbstractSensorConfig> sensorsConfig) {
        this.writeTimeoutConfig = writeTimeoutConfig;
        this.updateRate = updateRate;
        this.connectionConfig = connectionConfig;
        this.sensorsConfig = new ArrayList<>(sensorsConfig);
    }

    public TimeoutConfig getWriteTimeout() {
        return writeTimeoutConfig;
    }

    public TimeoutConfig getUpdateRate() {
        return updateRate;
    }

    public ConnectionConfig getConnection() {
        return connectionConfig;
    }

    public List<AbstractSensorConfig> getSensors() {
        return new ArrayList<>(sensorsConfig);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplicationConfig that = (ApplicationConfig) o;
        return Objects.equals(writeTimeoutConfig, that.writeTimeoutConfig) &&
                Objects.equals(connectionConfig, that.connectionConfig) &&
                Objects.equals(sensorsConfig, that.sensorsConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writeTimeoutConfig, connectionConfig, sensorsConfig);
    }
}
