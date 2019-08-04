package info.mike.dynatraceapp.service;

import info.mike.dynatraceapp.repository.MetricEntity;
import info.mike.dynatraceapp.repository.persistence.MetricsRepository;
import info.mike.dynatraceapp.utils.StringUtils;
import info.mike.dynatraceapp.web.transfer.MetricEntry;
import info.mike.dynatraceapp.web.transfer.MetricsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.scheduler.forkjoin.ForkJoinPoolScheduler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class MetricsService implements ApplicationListener<ContextRefreshedEvent> {

    private MetricsEndpoint metricsEndpoint;
    private final MetricsRepository metricsRepository;
    private final Scheduler taskScheduler;
    private final Set<String> metricsNames;

    private static final Logger LOG = LoggerFactory.getLogger(MetricsService.class);

    public MetricsService(MetricsEndpoint metricsEndpoint, MetricsRepository metricsRepository) {
        this.metricsEndpoint = metricsEndpoint;
        this.metricsRepository = metricsRepository;
        this.taskScheduler = ForkJoinPoolScheduler.create("metricsScheduler");
        this.metricsNames = metricsEndpoint.listNames().getNames();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        taskScheduler.schedulePeriodically(() -> insertAllMetricsToDatabase().subscribe(), 20, 10, TimeUnit.SECONDS);
    }

    public Flux<MetricsResponse> prepareResponse() {
        return Flux.fromIterable(metricsNames)
            .flatMap(metricName -> {
                return metricsRepository.findFirst10ByNameOrderByDateDesc(metricName)
                    .collectList()
                    .map(metricList -> createMetricsResponse(metricList));
            }).sort(Comparator.comparing(MetricsResponse::getName));
    }

    private MetricsResponse createMetricsResponse(List<MetricEntity> metricList) {
        return new MetricsResponse(StringUtils.dotSeparatedToCapitalized(metricList.get(0).getName()),
            metricList.get(0).getDescription(),
            metricList.get(0).getUnit(),
            mapMetricsListToEntries(metricList));
    }

    private Flux<MetricEntity> insertAllMetricsToDatabase() {
        return Flux.fromIterable(metricsNames)
            .map(metricName -> metricsEndpoint.metric(metricName, Arrays.asList()))
            .map(metric -> new MetricEntity(metric.getName(), metric.getDescription(),
                metric.getBaseUnit(), metric.getMeasurements().get(0).getValue()))
            .flatMap(metricsRepository::save);
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
}
