package com.rchiarinelli.eventsource;

import java.time.Duration;

import com.rchiarinelli.eventsource.configuration.RibbonConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@RibbonClient(name = "garagecore",configuration =  RibbonConfiguration.class)//targets to garagecore service
@Log4j2
public class EventSourceApplication {

	@LoadBalanced
	@Bean
	RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		final RestTemplate t = restTemplateBuilder.setReadTimeout(Duration.ofSeconds(30)).setConnectTimeout(Duration.ofSeconds(30)).build();
		return t;
	}

	public static void main(String[] args) {
		log.info("Starting EventSource application.");
		SpringApplication.run(EventSourceApplication.class, args);
	}

}
