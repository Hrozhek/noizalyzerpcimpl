package sensor;

public abstract class AbstractSensor {

    protected final int id;

    protected AbstractSensor(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract void init();

    public abstract byte[] read();
}
