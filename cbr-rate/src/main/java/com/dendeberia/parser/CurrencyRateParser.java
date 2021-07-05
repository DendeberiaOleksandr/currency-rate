package com.dendeberia.parser;

import com.dendeberia.domain.CurrencyRate;

import java.util.List;

public interface CurrencyRateParser {
    List<CurrencyRate> parse(String ratesAsString);
}
