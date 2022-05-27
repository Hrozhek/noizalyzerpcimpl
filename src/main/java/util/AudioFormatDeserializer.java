package util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;

public class AudioFormatDeserializer extends StdDeserializer<AudioFormat> {

    public AudioFormatDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public AudioFormat deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        float sampleRate = node.get("sample_rate").asInt(8000);
        int sampleSize = node.get("sample_size_in_bits").asInt(16);
        int channels = node.get("channels").asInt(1);
        boolean signed = node.get("signed").asBoolean(true);
        boolean bigEndian = node.get("big_endian").asBoolean(true);
        return new AudioFormat(sampleRate, sampleSize, channels, signed, bigEndian);
    }
}