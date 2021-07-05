package com.dendeberia.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CachedCurrencyRates {
    private List<CurrencyRate> currencyRates;
}
