package info.mike.dynatraceapp.repository.persistence;

import info.mike.dynatraceapp.repository.CurrencyEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CurrencyRepository extends ReactiveMongoRepository<CurrencyEntity, String> {

    Mono<CurrencyEntity> findFirst1ByCodeOrderByDateDesc(String code);
}
