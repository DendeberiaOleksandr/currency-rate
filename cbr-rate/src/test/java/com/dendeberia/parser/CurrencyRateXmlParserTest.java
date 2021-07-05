package com.dendeberia.parser;


import com.dendeberia.domain.CurrencyRate;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyRateXmlParserTest {

    @Test
    void parseCurrencyRateTest() throws URISyntaxException, IOException {
        CurrencyRateXmlParser parser = new CurrencyRateXmlParser();
        Path path = Paths.get(ClassLoader.getSystemResource("cbr.xml").toURI());
        String xmlAsString = new String(Files.readAllBytes(path));

        List<CurrencyRate> rates = parser.parse(xmlAsString);

        assertEquals(34, rates.size());
        assertTrue(rates.contains(getEURRate()));
        assertTrue(rates.contains(getUSDRate()));
    }

    private CurrencyRate getUSDRate(){
        return CurrencyRate.builder()
                .numCode("840")
                .charCode("USD")
                .nominal("1")
                .name("Доллар США")
                .value("73,6175")
                .build();
    }

    private CurrencyRate getEURRate(){
        return CurrencyRate.builder()
                .numCode("978")
                .charCode("EUR")
                .nominal("1")
                .name("Евро")
                .value("87,0748")
                .build();
    }
}
