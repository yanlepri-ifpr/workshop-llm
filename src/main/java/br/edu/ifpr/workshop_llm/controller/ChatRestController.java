package br.edu.ifpr.workshop_llm.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import br.edu.ifpr.workshop_llm.dto.ChatRequestDTO;
import br.edu.ifpr.workshop_llm.dto.ChatResponseDTO;
import br.edu.ifpr.workshop_llm.dto.APIRequestDTO;
import br.edu.ifpr.workshop_llm.dto.APIResponseDTO;

@RestController
@RequestMapping("/chat/rest")
public class ChatRestController {
    private HttpClient httpClient;

    public ChatRestController() {
        this.httpClient = HttpClient.newHttpClient();
    }

    @PostMapping("")
    public ResponseEntity<ChatResponseDTO> prompt(@RequestBody ChatRequestDTO body) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            APIRequestDTO apiRequest = new APIRequestDTO("gemini-3.1-flash-lite", body.prompt());
            String jsonBody = mapper.writeValueAsString(apiRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/interactions"))
                    .header("Content-Type", "application/json")
                    .header("x-goog-api-key", "API_KEY")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
    
            HttpResponse<String> apiResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

            APIResponseDTO mappedResponse = mapper.readValue(apiResponse.body(), APIResponseDTO.class);
            String answer = mappedResponse
                .steps()
                .get(1)
                .content()
                .get(0)
                .text();

            ChatResponseDTO response = new ChatResponseDTO(answer);
            
            return ResponseEntity.ok(response);
        } catch (IOException err) {
            System.out.println(err);
        } catch (InterruptedException err) {
            System.out.println(err);
        }

        return null;
    }
}
