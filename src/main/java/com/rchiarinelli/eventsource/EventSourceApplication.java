package com.rchiarinelli.eventsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class EventSourceApplication {

	public static void main(String[] args) {
		log.info("Starting EventSource application.");
		SpringApplication.run(EventSourceApplication.class, args);
	}

}
