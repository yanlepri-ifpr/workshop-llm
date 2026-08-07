package br.edu.ifpr.workshop_llm.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpr.workshop_llm.dto.ChatRequestDTO;
import br.edu.ifpr.workshop_llm.dto.ChatResponseDTO;

@RestController
public class ChatRestController {
    private HttpClient httpClient;

    public ChatRestController() {
        this.httpClient = HttpClient.newHttpClient();
    }

    @PostMapping("/chat/rest")
    public ResponseEntity<ChatResponseDTO> rest(@RequestBody ChatRequestDTO body) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("url"))
                    .GET()
                    .build();
    
            HttpResponse<String> llmResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    
            ChatResponseDTO response = new ChatResponseDTO(llmResponse.body());
            
            return ResponseEntity.ok(response);
        } catch (IOException err) {
            System.out.println(err);
        } catch (InterruptedException err) {
            System.out.println(err);
        }

        return null;
    }
}
