package info.mike.dynatraceapp.service;

import info.mike.dynatraceapp.model.Currency;
import info.mike.dynatraceapp.repository.CurrencyEntity;
import info.mike.dynatraceapp.repository.persistence.CurrencyRepository;
import info.mike.dynatraceapp.service.api.CurrencyDataResponse;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.scheduler.forkjoin.ForkJoinPoolScheduler;

import java.util.concurrent.TimeUnit;

@Service
public class NbpApiService implements ApplicationListener<ContextRefreshedEvent> {

    private WebClient webClient;
    private CurrencyRepository currencyRepository;

    public NbpApiService(WebClient webClient, CurrencyRepository currencyRepository) {
        this.webClient = webClient;
        this.currencyRepository = currencyRepository;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    }

    public Flux<Currency> fetchData(String currencyCode) {
        return webClient.get()
            .uri("/exchangerates/rates/A/" + currencyCode + "/last/7")
            .retrieve()
            .bodyToFlux(Currency.class);
    }
}
