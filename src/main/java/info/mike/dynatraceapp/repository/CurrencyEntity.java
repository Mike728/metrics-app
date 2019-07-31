package info.mike.dynatraceapp.repository;

import info.mike.dynatraceapp.model.Currency;
import info.mike.dynatraceapp.model.Rate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Document
public class CurrencyEntity {

    @Id
    private String id;
    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;
    private String date;

    public CurrencyEntity() {
    }

    public CurrencyEntity(Currency currency) {
        this.table = currency.getTable();
        this.currency = currency.getCurrency();
        this.code = currency.getCode();
        this.rates = currency.getRates();
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getId() {
        return id;
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

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "CurrencyEntity{" +
            "id='" + id + '\'' +
            ", table='" + table + '\'' +
            ", currency='" + currency + '\'' +
            ", code='" + code + '\'' +
            ", rates=" + rates +
            ", date='" + date + '\'' +
            '}';
    }
}
