package com.rchiarinelli.eventsource.k8s;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "bean")
@Getter
@Setter
public class ClientConfig {
 
    private String message = "Message from backend is: %s <br/> Services : %s";
 
    // getters and setters
}