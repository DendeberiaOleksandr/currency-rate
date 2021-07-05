package com.dendeberia.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cbr-rate-client")
@Data
public class CbrRateClientConfig {
    private String url;
}
