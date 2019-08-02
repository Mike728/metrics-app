package info.mike.dynatraceapp.web.transfer;

import java.util.List;

public class MetricsResponse {

    private String name;
    private String description;
    private List<MetricEntry> metricEntries;

    public MetricsResponse(String name, String description , List<MetricEntry> metricEntries) {
        this.name = name;
        this.description = description;
        this.metricEntries = metricEntries;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<MetricEntry> getMetricEntries() {
        return metricEntries;
    }
}
