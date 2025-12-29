package com.link.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();
    private final Cors cors = new Cors();

    @Data
    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;
    }

    @Data
    public static class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();
    }

    @Data
    public static class Cors {
        private String[] allowedOrigins;
    }
}
