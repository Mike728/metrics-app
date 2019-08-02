package info.mike.dynatraceapp.web.transfer;

import java.util.List;

public class CurrencyResponse {

    private List<MidEntry> midEntries;
    private Double stdDev;
    private Double average;

    public CurrencyResponse() {
    }

    public CurrencyResponse(List<MidEntry> midEntries, Double stdDev, Double average) {
        this.midEntries = midEntries;
        this.stdDev = stdDev;
        this.average = average;
    }

    public List<MidEntry> getMidEntries() {
        return midEntries;
    }

    public Double getStdDev() {
        return stdDev;
    }

    public Double getAverage() {
        return average;
    }
}
