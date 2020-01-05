package com.rchiarinelli.eventsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/info")
public class EventSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventSourceApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
	  return "Hello Docker World";
	}

}
