package com.dendeberia.controller;

import com.dendeberia.domain.CurrencyRate;
import com.dendeberia.service.CachedCurrencyRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dendeberia.service.CurrencyRateService;

import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping(path = "${app.rest.api.prefix}/v1")
public class CurrencyRateController {
    private final CachedCurrencyRateService currencyRateService;

    @Autowired
    public CurrencyRateController(CachedCurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @GetMapping("/currencyRate/{currency}/{date}")
    public CurrencyRate getCurrencyRate(@PathVariable String currency,
                                        @PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy")LocalDate date){
        log.info("GET currency rate, currency:{}, date:{}", currency, date);
        CurrencyRate currencyRate = currencyRateService.getCurrencyRate(currency, date);
        log.info("currencyRate:{}", currencyRate);
        return currencyRate;
    }
}
