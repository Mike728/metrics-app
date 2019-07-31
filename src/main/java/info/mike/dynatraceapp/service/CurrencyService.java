package info.mike.dynatraceapp.service;

import info.mike.dynatraceapp.model.Rate;
import info.mike.dynatraceapp.repository.CurrencyEntity;
import info.mike.dynatraceapp.repository.persistence.CurrencyRepository;
import info.mike.dynatraceapp.web.transfer.CurrencyResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
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
            .map(currencyEntity -> new CurrencyResponse(getMidMap(currencyEntity), countStdDev(currencyEntity),
                countAverage(currencyEntity)));
    }

    public Map<String, Double> getMidMap(CurrencyEntity currencyEntity) {
        return currencyEntity
            .getRates()
            .stream()
            .collect(Collectors.toMap(Rate::getEffectiveDate, Rate::getMid));

//        Mono<Map<String, Double>> usd = currencyRepository
//            .findFirst1ByCodeOrderByDateDesc("USD")
//            .flatMapIterable(CurrencyEntity::getRates)
//            .collectMap(Rate::getEffectiveDate, Rate::getMid);
//
//        return Mono.empty();
    }

    public Double countAverage(CurrencyEntity currencyEntity) {
        Double collect = currencyEntity
            .getRates()
            .stream()
            .collect(Collectors.averagingDouble(Rate::getMid));
        return collect;
    }

    public Double countStdDev(CurrencyEntity currencyEntity) {
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
