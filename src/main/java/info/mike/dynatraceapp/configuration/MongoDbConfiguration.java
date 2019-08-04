package info.mike.dynatraceapp.configuration;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDbConfiguration {

    @Bean
    public MongoClientOptions mongoOptions() {
        return MongoClientOptions.builder()
            .maxWaitTime(300000)
            .connectionsPerHost(90).build();
    }
}
