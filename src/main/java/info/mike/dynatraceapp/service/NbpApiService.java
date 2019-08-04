package info.mike.dynatraceapp.service;

import info.mike.dynatraceapp.model.Currency;
import info.mike.dynatraceapp.repository.CurrencyEntity;
import info.mike.dynatraceapp.repository.persistence.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.scheduler.forkjoin.ForkJoinPoolScheduler;

import java.util.concurrent.TimeUnit;

@Service
public class NbpApiService implements ApplicationListener<ContextRefreshedEvent> {

    private final WebClient webClient;
    private final CurrencyRepository currencyRepository;
    private final Scheduler taskScheduler;

    private static final Logger LOG = LoggerFactory.getLogger(NbpApiService.class);

    public NbpApiService(WebClient webClient, CurrencyRepository currencyRepository) {
        this.webClient = webClient;
        this.currencyRepository = currencyRepository;
        this.taskScheduler = ForkJoinPoolScheduler.create("apiScheduler");
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        taskScheduler.schedulePeriodically(() -> {
            prepareApiRequest("EUR").subscribe();
            prepareApiRequest("USD").subscribe();
        }, 20, 120, TimeUnit.SECONDS);
    }

    private Flux<Currency> fetchData(String currencyCode) {
        return webClient.get()
            .uri("/exchangerates/rates/A/" + currencyCode + "/last/7")
            .retrieve()
            .bodyToFlux(Currency.class);
    }

    private Flux<CurrencyEntity> prepareApiRequest(String currencyCode) {
        return fetchData(currencyCode)
            .map(CurrencyEntity::new)
            .flatMap(currencyRepository::save)
            .doOnComplete(() -> LOG.info("Saved successfully " + currencyCode));
    }
}
