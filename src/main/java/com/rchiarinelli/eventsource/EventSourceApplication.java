package com.rchiarinelli.eventsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EventSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventSourceApplication.class, args);
	}

	@RequestMapping("/")
	public String home() {
	  return "Hello Docker World";
	}

}
