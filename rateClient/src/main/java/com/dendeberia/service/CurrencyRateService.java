package com.dendeberia.service;

import com.dendeberia.client.RateClient;
import com.dendeberia.model.CurrencyRate;
import com.dendeberia.model.CurrencySourceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@Slf4j
public class CurrencyRateService {

    private final Map<String, RateClient> clients;

    public CurrencyRateService(Map<String, RateClient> clients) {
        this.clients = clients;
    }

    public CurrencyRate getCurrencyRate(CurrencySourceType source, String charCode, LocalDate date){
        log.info("Get currency rate: from source:{}, charCode:{}, date:{}", source, charCode, date);
        RateClient rateClient = clients.get(source.getServiceName());
        return rateClient.getCurrencyRate(charCode, date);
    }
}
