package com.dendeberia;

import com.dendeberia.config.CachingConfig;
import com.dendeberia.config.CbrConfig;
import com.dendeberia.controller.CurrencyRateController;
import com.dendeberia.parser.CurrencyRateParser;
import com.dendeberia.parser.CurrencyRateXmlParser;
import com.dendeberia.requester.CbrRequester;
import com.dendeberia.service.CachedCurrencyRateService;
import com.dendeberia.service.CurrencyRateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    private CurrencyRateController currencyRateController;
    @Autowired
    private CurrencyRateService currencyRateService;
    @Autowired
    private CbrRequester cbrRequester;
    @Autowired
    private CurrencyRateParser currencyRateXmlParser;
    @Autowired
    private CachingConfig cachingConfig;
    @Autowired
    private CbrConfig cbrConfig;

    @Test
    public void contextLoads(){
        Assertions.assertNotEquals(null, currencyRateController);
        Assertions.assertNotEquals(null, currencyRateService);
        Assertions.assertNotEquals(null, cbrRequester);
        Assertions.assertNotEquals(null, currencyRateXmlParser);
        Assertions.assertNotEquals(null, cachingConfig);
        Assertions.assertNotEquals(null, cbrConfig);
    }
}
