package info.mike.dynatraceapp.service;

import info.mike.dynatraceapp.repository.CurrencyEntity;
import info.mike.dynatraceapp.repository.persistence.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.scheduler.forkjoin.ForkJoinPoolScheduler;

import java.util.concurrent.TimeUnit;

@Service
public class SchedulersService implements ApplicationListener<ContextRefreshedEvent> {

    private final NbpApiService nbpApiService;
    private final CurrencyRepository currencyRepository;
    private final Scheduler taskScheduler;

    private static final Logger LOG = LoggerFactory.getLogger(SchedulersService.class);

    public SchedulersService(NbpApiService nbpApiService, CurrencyRepository currencyRepository) {
        this.nbpApiService = nbpApiService;
        this.currencyRepository = currencyRepository;
        this.taskScheduler = ForkJoinPoolScheduler.create("apiScheduler");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        scheduleTask();
    }

    public void scheduleTask() {
        taskScheduler.schedulePeriodically(() -> {
            prepareApiRequest("EUR").subscribe();
            prepareApiRequest("USD").subscribe();
        }, 1, 500, TimeUnit.SECONDS);
    }

    public Flux<CurrencyEntity> prepareApiRequest(String currencyCode) {
        return nbpApiService
            .fetchData(currencyCode)
            .map(CurrencyEntity::new)
            .flatMap(currencyRepository::save)
            .doOnComplete(() -> LOG.info("Saved successfully " + currencyCode));
    }
}
