package com.dendeberia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CurrencyRate {
    private String charCode;
    private String numCode;
    private String nominal;
    private String name;
    private String value;

    @JsonCreator
    public CurrencyRate(@JsonProperty("charCode") String charCode,
                        @JsonProperty("numCode") String numCode,
                        @JsonProperty("nominal") String nominal,
                        @JsonProperty("name") String name,
                        @JsonProperty("value") String value) {
        this.charCode = charCode;
        this.numCode = numCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }
}
