package info.mike.dynatraceapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl("http://api.nbp.pl/api")
            .defaultHeader(HttpHeaders.ACCEPT, "application/json")
            .build();
    }
}
