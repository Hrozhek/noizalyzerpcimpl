package config;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TimeoutConfig {
    private final long time;
    private final TimeUnit timeUnit;


    public TimeoutConfig(long time, TimeUnit timeUnit) {
        this.time = time;
        this.timeUnit = timeUnit;
    }

    public long getTime() {
        return time;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeoutConfig timeoutConfig = (TimeoutConfig) o;
        return time == timeoutConfig.time && timeUnit == timeoutConfig.timeUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, timeUnit);
    }
}
