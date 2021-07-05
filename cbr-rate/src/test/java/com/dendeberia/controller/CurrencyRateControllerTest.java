package com.dendeberia.controller;

import com.dendeberia.config.CachingConfig;
import com.dendeberia.config.CbrConfig;
import com.dendeberia.parser.CurrencyRateXmlParser;
import com.dendeberia.requester.CbrRequester;
import com.dendeberia.service.CachedCurrencyRateService;
import com.dendeberia.service.CurrencyRateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurrencyRateController.class)
@Import({CachingConfig.class, CachedCurrencyRateService.class, CurrencyRateXmlParser.class})
public class CurrencyRateControllerTest {
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CbrConfig cbrConfig;

    @MockBean
    private CbrRequester cbrRequester;

    @Test
    public void getCurrencyRateTest() throws Exception {
        mockCurrencyRateController(null);

        String currency = "EUR";
        String date = "02-07-2021";

        mockMvc.perform(get(String.format("/api/v1/currencyRate/%s/%s", currency, date))).andExpect(status().isOk());
        mockMvc.perform(get(String.format("/api/v1/currencyRate/%s/%s", currency, date))).andExpect(status().isOk());

        currency = "USD";
        mockMvc.perform(get(String.format("/api/v1/currencyRate/%s/%s", currency, date))).andExpect(status().isOk());

        date = "05-03-2021";
        mockMvc.perform(get(String.format("/api/v1/currencyRate/%s/%s", currency, date))).andExpect(status().isOk());
    }

    private void mockCurrencyRateController(String date) throws URISyntaxException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource("cbr.xml").toURI());
        String xmlAsString = new String(Files.readAllBytes(path));

        if(date == null){
            when(cbrRequester.getRatesAsXml(any())).thenReturn(xmlAsString);
        } else {
            LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER);
            String url = String.format("%s?date_req=%s", cbrConfig.getUrl());
            when(cbrRequester.getRatesAsXml(url)).thenReturn(xmlAsString);
        }
    }
}
