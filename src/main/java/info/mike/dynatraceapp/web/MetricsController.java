package info.mike.dynatraceapp.web;

import info.mike.dynatraceapp.service.MetricsService;
import info.mike.dynatraceapp.web.transfer.MetricsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/metrics")
    public Flux<MetricsResponse> prepareMetrics() {
        return metricsService.prepareResponse();
    }
}
