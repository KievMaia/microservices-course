package br.com.kievmaia.greetingservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "greeting-service")
@RefreshScope
@Data
public class GreetingConfiguration {
    private String greeting;
    private String defaultValue;
}