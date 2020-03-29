package com.rchiarinelli.eventsource;

import com.rchiarinelli.eventsource.configuration.RibbonConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@RibbonClient(name = "ambassador",configuration =  RibbonConfiguration.class)//targets to garagecore service
public class EventSourceApplication {

	@LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
	}

	public static void main(String[] args) {
		log.info("Starting EventSource application.");
		SpringApplication.run(EventSourceApplication.class, args);
	}

}
