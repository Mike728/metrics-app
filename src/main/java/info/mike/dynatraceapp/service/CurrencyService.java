package info.mike.dynatraceapp.service;

import info.mike.dynatraceapp.model.Rate;
import info.mike.dynatraceapp.repository.CurrencyEntity;
import info.mike.dynatraceapp.repository.persistence.CurrencyRepository;
import info.mike.dynatraceapp.web.transfer.CurrencyResponse;
import info.mike.dynatraceapp.web.transfer.MidEntry;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Mono<CurrencyResponse> prepareResponse(String code) {
        return currencyRepository
            .findFirst1ByCodeOrderByDateDesc(code)
            .map(currencyEntity -> new CurrencyResponse(getMidList(currencyEntity), countStdDev(currencyEntity),
                countAverage(currencyEntity)));
    }

    private List<MidEntry> getMidList(CurrencyEntity currencyEntity) {
        return currencyEntity
            .getRates()
            .stream()
            .map(rate -> new MidEntry(rate.getEffectiveDate(), rate.getMid()))
            .collect(Collectors.toList());
    }

    private Double countAverage(CurrencyEntity currencyEntity) {
        return currencyEntity
            .getRates()
            .stream()
            .collect(Collectors.averagingDouble(Rate::getMid));
    }

    private Double countStdDev(CurrencyEntity currencyEntity) {
        double temp = 0;
        List<Double> collect = currencyEntity
            .getRates()
            .stream()
            .map(Rate::getMid)
            .collect(Collectors.toList());

        for(double ask : collect) {
            temp += Math.pow((ask - countAverage(currencyEntity)), 2);
        }

        return Math.sqrt((temp / collect.size()));
    }
}
