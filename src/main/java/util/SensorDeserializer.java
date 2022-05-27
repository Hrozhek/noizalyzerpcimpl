package util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import config.TimeoutConfig;
import config.sensor.AbstractSensorConfig;
import config.sensor.MicrophoneSensorConfig;
import sensor.AbstractSensor;
import sensor.Microphone;
import sensor.SensorType;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SensorDeserializer extends StdDeserializer<AbstractSensorConfig> {

    public SensorDeserializer() {
        super(Object.class);
    }

    public SensorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public AbstractSensorConfig deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        if (true) {
            return new MicrophoneSensorConfig(new AudioFormat(8000.0f, 16, 1, true, true), "fake", new TimeoutConfig(3, TimeUnit.SECONDS));//todo
        }
        SensorType type = SensorType.valueOf(node.get("type").toString().toUpperCase(Locale.ENGLISH));
//        TimeoutConfig readTimeout = node.get("read_duration");
        return switch (type) {
            case MICROPHONE -> microphone(node);
            default -> throw new RuntimeException();//todo
        };
    }

    public MicrophoneSensorConfig microphone(JsonNode node) {
        String name = node.get("name").toString();
        return null;
    }
}
