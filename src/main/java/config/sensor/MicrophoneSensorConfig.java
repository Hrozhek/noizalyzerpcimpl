package config.sensor;

import com.fasterxml.jackson.annotation.JsonCreator;
import config.TimeoutConfig;
import sensor.SensorType;

import javax.sound.sampled.AudioFormat;
import java.util.Objects;

public class MicrophoneSensorConfig extends AbstractSensorConfig {

    private final AudioFormat format;
    private final String name;

    @JsonCreator
    public MicrophoneSensorConfig(AudioFormat format, String name, TimeoutConfig readDuration) {
        super(SensorType.MICROPHONE, readDuration);
        this.format = format;
        this.name = name;
    }

    public AudioFormat getFormat() {
        return format;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MicrophoneSensorConfig that = (MicrophoneSensorConfig) o;
        return Objects.equals(format, that.format) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(format, name);
    }
}
