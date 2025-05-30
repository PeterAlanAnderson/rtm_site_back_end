package com.rtm_backend.primary;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PrimaryApplication {
	private final StartGGService startGGService;

	public PrimaryApplication(StartGGService startGGService) {
		this.startGGService = startGGService;
	}

	public static void main(String[] args) {
		SpringApplication.run(PrimaryApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s", name);
	}

	@GetMapping("/upcoming_tournaments")
	// public Map<String, Object> getUpcomingTournaments() {
		public String getUpcomingTournaments() {

		// This method should call the Start.gg API using the apiToken
		// and return a list of upcoming tournaments.
		// For now, we will return a placeholder response.
		return this.startGGService.getUpcomingTournaments();
		// Map<String, Object> response = new HashMap<>();
		// response.put("message", "This endpoint will return upcoming tournaments.");
		// return response;
	}

}
