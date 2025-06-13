package com.hei.school.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HazavaoService {

  @Value("${OPENAI_API_KEY}")
  private String apiKey;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public String getDefinition(String teny) throws Exception {
    HttpClient client = HttpClient.newHttpClient();

    Map<String, Object> message = new HashMap<>();
    message.put("role", "user");
    message.put("content", "Hazavao amin’ny teny malagasy ny dikan’ny teny : " + teny);

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("model", "gpt-3.5-turbo");
    requestBody.put("messages", List.of(message));

    String body = objectMapper.writeValueAsString(requestBody);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(new URI("https://api.openai.com/v1/chat/completions"))
            .timeout(Duration.ofSeconds(30))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + apiKey)
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    Map<String, Object> jsonResponse = objectMapper.readValue(response.body(), Map.class);
    List<Map<String, Object>> choices = (List<Map<String, Object>>) jsonResponse.get("choices");
    Map<String, Object> messageResponse = (Map<String, Object>) choices.get(0).get("message");

    return (String) messageResponse.get("content");
  }
}
