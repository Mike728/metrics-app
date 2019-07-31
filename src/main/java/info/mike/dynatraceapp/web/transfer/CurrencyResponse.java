package info.mike.dynatraceapp.web.transfer;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;
import java.util.Map;

public class CurrencyResponse {

    private Map<String, Double> mid;
    private Double stdDev;
    private Double average;

    public CurrencyResponse() {
    }

    public CurrencyResponse(Map<String, Double> mid, Double stdDev, Double average) {
        this.mid = mid;
        this.stdDev = stdDev;
        this.average = average;
    }

    public Map<String, Double> getMid() {
        return mid;
    }

    public Double getStdDev() {
        return stdDev;
    }

    public Double getAverage() {
        return average;
    }
}
