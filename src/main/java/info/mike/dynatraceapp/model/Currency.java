package info.mike.dynatraceapp.model;

import java.util.List;

public class Currency {

    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;

    public Currency() {
    }

    public String getTable() {
        return table;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    public List<Rate> getRates() {
        return rates;
    }
}
