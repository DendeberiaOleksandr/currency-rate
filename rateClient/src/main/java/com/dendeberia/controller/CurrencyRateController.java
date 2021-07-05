package com.dendeberia.controller;

import com.dendeberia.model.CurrencyRate;
import com.dendeberia.model.CurrencySourceType;
import com.dendeberia.service.CurrencyRateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(path = "{app.rest.api.prefix}/v1")
public class CurrencyRateController {
    private final CurrencyRateService currencyRateService;

    @GetMapping("/currencyRate/{type}/{charCode}/{date}")
    public CurrencyRate getCurrencyRate(@PathVariable CurrencySourceType type,
                                        @PathVariable String charCode,
                                        @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable LocalDate date){
        log.info("GET currency rate type:{}, charCode:{}, date:{}", type.name(), charCode, date);
        CurrencyRate currencyRate = currencyRateService.getCurrencyRate(type, charCode, date);
        return currencyRate;
    }
}
