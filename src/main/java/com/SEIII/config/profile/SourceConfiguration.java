package com.SEIII.config.profile;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "source")
public class SourceConfiguration {
    private Config config;

    @Data
    public static class Config {
        private String url;
        private String group;
    }
}
