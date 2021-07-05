package com.dendeberia.service;

import com.dendeberia.domain.CurrencyRate;

import java.time.LocalDate;

public interface CurrencyRateService {

    CurrencyRate getCurrencyRate(String name, LocalDate date);
}
