package info.mike.dynatraceapp.web.transfer;

import java.util.List;

public class MetricsResponse {

    private String name;
    private String description;
    private String unit;
    private List<MetricEntry> metricEntries;

    public MetricsResponse(String name, String description, String unit, List<MetricEntry> metricEntries) {
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.metricEntries = metricEntries;
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

    public List<MetricEntry> getMetricEntries() {
        return metricEntries;
    }
}
