package info.mike.dynatraceapp.service;

import info.mike.dynatraceapp.repository.MetricEntity;
import info.mike.dynatraceapp.repository.persistence.MetricsRepository;
import info.mike.dynatraceapp.utils.StringUtils;
import info.mike.dynatraceapp.web.transfer.MetricEntry;
import info.mike.dynatraceapp.web.transfer.MetricsResponse;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.scheduler.forkjoin.ForkJoinPoolScheduler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MetricsService implements ApplicationListener<ContextRefreshedEvent> {

    private MetricsEndpoint metricsEndpoint;
    private MetricsRepository metricsRepository;
    private final Scheduler taskScheduler;
    private final Set<String> metricsNames;

    public MetricsService(MetricsEndpoint metricsEndpoint, MetricsRepository metricsRepository) {
        this.metricsEndpoint = metricsEndpoint;
        this.metricsRepository = metricsRepository;
        this.taskScheduler = ForkJoinPoolScheduler.create("metricsScheduler");
        this.metricsNames = metricsEndpoint.listNames().getNames();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        taskScheduler.schedulePeriodically(() -> insertAllMetricsToDatabase().subscribe(), 20, 5, TimeUnit.SECONDS);
    }

    public Flux<MetricsResponse> prepareResponse() {
        return Flux.fromIterable(metricsNames)
            .flatMap(metricName -> {
                return metricsRepository.findFirst10ByNameOrderByDateDesc(metricName)
                    .collectList()
                    .map(metricList -> new MetricsResponse(StringUtils.dotSeparatedToCapitalized(metricList.get(0).getName()),
                        metricList.get(0).getDescription(),
                        mapMetricsListToEntries(metricList)));
            }).sort(Comparator.comparing(MetricsResponse::getDescription));
    }

    private String convert(String text) {
        return text;
    }

    private List<MetricEntry> mapMetricsListToEntries(List<MetricEntity> list) {
        return list
            .stream()
            .map(metricEntity -> new MetricEntry(metricEntity.getValue(), metricEntity.getDate()))
            .sorted(Comparator.comparing(MetricEntry::getDate))
            .collect(Collectors.toList());
    }

    private List<Double> mapMetricsListToValues(List<MetricEntity> list) {
        return list
            .stream()
            .map(MetricEntity::getValue)
            .collect(Collectors.toList());
    }

    private Flux<MetricEntity> insertAllMetricsToDatabase() {
        return Flux.fromIterable(metricsNames)
            .map(metricName -> metricsEndpoint.metric(metricName, Arrays.asList()))
            .map(metric -> new MetricEntity(metric.getName(), metric.getDescription(),
                metric.getBaseUnit(), metric.getMeasurements().get(0).getValue()))
            .flatMap(metricsRepository::save);
    }
}
