package com.dendeberia.client;

import com.dendeberia.config.CbrRateClientConfig;
import com.dendeberia.model.CurrencyRate;
import com.dendeberia.model.CurrencySourceType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service("cbr")
@AllArgsConstructor
@Slf4j
public class CbrClient implements RateClient{
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final CbrRateClientConfig config;
    private final ObjectMapper objectMapper;

    @Override
    public CurrencyRate getCurrencyRate(String charCode, LocalDate date) {
        log.info("getCurrencyRate charCode:{}, date:{}", charCode, date);

        String url = String.format("%s/%s/%s", config.getUrl(), charCode, DATE_FORMATTER.format(date));
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), CurrencyRate.class);
        } catch (Exception e) {
            log.error("Error when sending request url:{}", url, e);
            throw new RuntimeException(e);
        }
    }
}
