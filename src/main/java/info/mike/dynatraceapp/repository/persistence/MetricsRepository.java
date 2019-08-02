package info.mike.dynatraceapp.repository.persistence;

import info.mike.dynatraceapp.repository.MetricEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MetricsRepository extends ReactiveMongoRepository<MetricEntity, String> {

    Flux<MetricEntity> findFirst10ByNameOrderByDateDesc(String name);
}
