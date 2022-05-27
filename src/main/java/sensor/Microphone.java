package sensor;

import config.TimeoutConfig;
import config.sensor.MicrophoneSensorConfig;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Microphone extends AbstractSensor {

    private final AudioFormat format;
    private final String name;
    private final TimeoutConfig readDuration;

    private volatile TargetDataLine microphone;

    public Microphone(int id, MicrophoneSensorConfig config) {
        super(id);
        format = config.getFormat();
        readDuration = config.getReadDuration();
        name = config.getName();
    }

    private Microphone() {
        super(12123);
        format = new AudioFormat(8000.0f, 16, 1, true, true);
        readDuration = new TimeoutConfig(3, TimeUnit.SECONDS);
        name = "none";
    }

    @Override
    public void /*todo return smth*/ init() {
//        if (microphone != null) {
//            return;
//        }
        //flag and not null
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        try {
            microphone = (TargetDataLine) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            e.printStackTrace();//todo
        }

    }

    @Override
    public void destroy() {

    }

    private byte[] doReadCycle(final TargetDataLine dataLine) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int numBytesRead;
            int CHUNK_SIZE = 1024;
            byte[] data = new byte[microphone.getBufferSize() / 5];
            dataLine.start();
            System.out.println("reading!!!");//todo log
            Instant end = Instant.now().plus(readDuration.getTime(), readDuration.getTimeUnit().toChronoUnit());
            while (Instant.now().isBefore(end)) {
                numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                out.write(data, 0, numBytesRead);
            }
            System.out.println("stop reading!!!");//todo log
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException();//todo
        }
    }

    @Override
    public byte[] read() {
        //        if (!inited) throw todo
        try (TargetDataLine dataLine = microphone) {
            dataLine.open(format);
            return doReadCycle(dataLine);
        } catch (LineUnavailableException e) {
            throw new RuntimeException();//todo
        }
    }

    private void write(byte[] sound) {//todo remove! just for checking
        SourceDataLine speakers;
        try {
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();
            speakers.write(sound, 0, sound.length);
            speakers.drain();
            speakers.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Instant now = Instant.now();
        Microphone m = new Microphone();
        m.init();
        byte[] input = m.read();
        System.out.println("writing!!! after " + Duration.between(now, Instant.now()));
        m.write(input);
    }
}
