package com.dendeberia.client;

import com.dendeberia.model.CurrencyRate;

import java.time.LocalDate;

public interface RateClient {
    CurrencyRate getCurrencyRate(String charCode, LocalDate date);
}
