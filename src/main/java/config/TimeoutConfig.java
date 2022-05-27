package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TimeoutConfig {

    private static final class Token {

        public static final String TIME = "time";
        public static final String TIME_UNIT = "time_unit";
    }

    private final long time;
    private final TimeUnit timeUnit;


    @JsonCreator
    public TimeoutConfig(@JsonProperty(Token.TIME) long time,
                         @JsonProperty(Token.TIME_UNIT) TimeUnit timeUnit) {
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
