package info.mike.dynatraceapp.web;

import info.mike.dynatraceapp.service.CurrencyService;
import info.mike.dynatraceapp.web.transfer.CurrencyRequest;
import info.mike.dynatraceapp.web.transfer.CurrencyResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @CrossOrigin
    @PostMapping("/fetch_data")
    public Mono<CurrencyResponse> receive(@RequestBody CurrencyRequest currencyRequest) {
        return currencyService.prepareResponse(currencyRequest.getCode());
    }
}

