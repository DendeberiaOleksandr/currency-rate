package com.dendeberia.parser;

import com.dendeberia.domain.CurrencyRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CurrencyRateXmlParser implements CurrencyRateParser{
    @Override
    public List<CurrencyRate> parse(String ratesAsString) {
        List<CurrencyRate> currencyRates = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(ratesAsString.getBytes());
            InputSource inputSource = new InputSource(inputStream);
            inputSource.setEncoding("UTF-8");
            Document document = builder.parse(inputSource);
            Element root = document.getDocumentElement();
            NodeList valutes = root.getElementsByTagName("Valute");
            for(int i = 0; i < valutes.getLength(); i++){
                Element valute = (Element) valutes.item(i);
                CurrencyRate rate = CurrencyRate.builder()
                        .name(valute.getElementsByTagName("Name").item(0).getTextContent())
                        .charCode(valute.getElementsByTagName("CharCode").item(0).getTextContent())
                        .value(valute.getElementsByTagName("Value").item(0).getTextContent())
                        .nominal(valute.getElementsByTagName("Nominal").item(0).getTextContent())
                        .numCode(valute.getElementsByTagName("NumCode").item(0).getTextContent())
                        .build();
                currencyRates.add(rate);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return currencyRates;
    }
}
