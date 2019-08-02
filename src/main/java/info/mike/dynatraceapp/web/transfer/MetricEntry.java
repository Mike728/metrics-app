package info.mike.dynatraceapp.web.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class MetricEntry {

    private Double value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalDateTime date;

    public MetricEntry() {
    }

    public MetricEntry(Double value, LocalDateTime date) {
        this.value = value;
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "MetricEntry{" +
            "value=" + value +
            ", date=" + date +
            '}';
    }
}
