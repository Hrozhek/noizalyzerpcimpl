import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import config.ApplicationConfig;
import config.sensor.AbstractSensorConfig;
import maincycle.MainCycle;
import util.AudioFormatDeserializer;
import util.SensorDeserializer;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class NoizealyzerApplication {

    private static final String CONFIG_NAME = "controllerconfig.json";

    public static void main(String[] args) throws IOException, URISyntaxException {
        ObjectMapper mapper = getMapper();
        Path configFile = Paths.get(Objects.requireNonNull(NoizealyzerApplication.class.getResource(CONFIG_NAME))
                .toURI());
        ApplicationConfig config = mapper.readValue(configFile.toFile(), ApplicationConfig.class);
        MainCycle.getInstance(config).start();
    }

    private static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule audioModule = new SimpleModule();
        audioModule.addDeserializer(AudioFormat.class, new AudioFormatDeserializer(AudioFormat.class));
        audioModule.addDeserializer(AbstractSensorConfig.class, new SensorDeserializer(AbstractSensorConfig.class));
        mapper.registerModule(audioModule);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
