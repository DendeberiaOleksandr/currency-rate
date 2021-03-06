package com.dendeberia.requester;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class CbrRequesterImpl implements CbrRequester{

    @Override
    public String getRatesAsXml(String url) {
        try {
            log.info("Request: GET rates as XML url:{}", url);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            if(e instanceof InterruptedException){
                Thread.currentThread().interrupt();
            }
            log.error("Cbr request error url:{}", url, e);
            throw new RuntimeException(e);
        }
    }

}
