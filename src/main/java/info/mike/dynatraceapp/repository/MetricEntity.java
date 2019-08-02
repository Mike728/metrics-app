package info.mike.dynatraceapp.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document
public class MetricEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private String unit;
    private Double value;
    @Indexed(expireAfterSeconds = 300)
    private LocalDateTime date;

    public MetricEntity() {
    }

    public MetricEntity(String name, String description, String unit, Double value) {
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.value = value;
        this.date = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }

    public Double getValue() {
        return value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "MetricEntity{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", unit='" + unit + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
