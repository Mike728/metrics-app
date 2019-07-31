package info.mike.dynatraceapp.web;

import info.mike.dynatraceapp.service.CurrencyService;
import info.mike.dynatraceapp.web.transfer.CurrencyRequest;
import info.mike.dynatraceapp.web.transfer.CurrencyResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/fetch_data")
    public Mono<CurrencyResponse> receive(@RequestBody CurrencyRequest currencyRequest) {
        return Mono.empty();
    }
}

