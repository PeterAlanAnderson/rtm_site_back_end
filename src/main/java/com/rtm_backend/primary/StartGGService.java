package com.rtm_backend.primary;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class StartGGService {
    private final WebClient webClient;
    private final String apiToken;

    public StartGGService(WebClient.Builder webClientBuilder) {
        this.apiToken = Dotenv.configure().load().get("START_GG_API_TOKEN");
        this.webClient = webClientBuilder
                .baseUrl("https://api.start.gg/gql/alpha")
                .defaultHeader("Authorization", "Bearer " + apiToken)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String getUpcomingTournaments() {
        String query = "query TournamentsByOwner($perPage: Int!, $ownerId: ID!) {\n" +
                       "  tournaments(query: {\n" +
                       "    perPage: $perPage\n" +
                       "    filter: {\n" +
                       "      ownerId: $ownerId\n" +
                       "    }\n" +
                       "  }) {\n" +
                       "    nodes {\n" +
                       "      id\n" +
                       "      name\n" +
                       "      slug\n" +
                       "    }\n" +
                       "  }\n}";
        String requestBody = String.format(
                "{\"query\": \"%s\", \"variables\": {\"perPage\": 5, \"ownerId\": \"830225\"}}",
                escapeJson(query)
        );
        System.out.println("REQUEST BODY: " + requestBody);
        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Blocking for simplicity, consider reactive patterns in production
    }

    private String escapeJson(String input) {
        return input.replace("\"", "\\\"").replace("\n", "\\n");
    }
}