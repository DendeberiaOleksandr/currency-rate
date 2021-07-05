package com.dendeberia.service;

import com.dendeberia.config.CbrConfig;
import com.dendeberia.domain.CachedCurrencyRates;
import com.dendeberia.domain.CurrencyRate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dendeberia.parser.CurrencyRateParser;
import com.dendeberia.requester.CbrRequester;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CachedCurrencyRateService implements CurrencyRateService{
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final CbrRequester requester;
    private final CurrencyRateParser parser;
    private final CbrConfig cbrConfig;
    private final Cache<LocalDate, CachedCurrencyRates> currencyRateCache;

    @Autowired
    public CachedCurrencyRateService(CbrRequester requester,
                                     CurrencyRateParser parser,
                                     CbrConfig cbrConfig,
                                     Cache<LocalDate, CachedCurrencyRates> currencyRateCache) {
        this.requester = requester;
        this.parser = parser;
        this.cbrConfig = cbrConfig;
        this.currencyRateCache = currencyRateCache;
    }

    @Override
    public CurrencyRate getCurrencyRate(String charCode, LocalDate date) {
        log.info("Get currency rate: charCode:{}, date:{}", charCode, date);

        List<CurrencyRate> rates = new ArrayList<>();

        CachedCurrencyRates cachedCurrencyRates = currencyRateCache.get(date);
        if(cachedCurrencyRates == null){
            String url = String.format("%s?date_req=%s", cbrConfig.getUrl(), DATE_FORMATTER.format(date));
            String ratesAsXml = requester.getRatesAsXml(url);
            rates = parser.parse(ratesAsXml);
            currencyRateCache.put(date, new CachedCurrencyRates(rates));
        } else {
            rates = cachedCurrencyRates.getCurrencyRates();
        }

        return rates.stream().filter(r -> r.getCharCode().equals(charCode)).findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find currency: charCode: " + charCode + ", date: " + date));
    }
}
