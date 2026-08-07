package br.edu.ifpr.workshop_llm.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpr.workshop_llm.dto.ChatRequestDTO;
import br.edu.ifpr.workshop_llm.dto.ChatResponseDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ChatLibController {
    @Value("${spring.ai.google.genai.api-key}")
    public String apiKey;

    private ChatClient chatClient;

    public ChatLibController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping("chat/lib")
    public ResponseEntity<ChatResponseDTO> genAI(@RequestBody ChatRequestDTO body) {
        String answer = chatClient.prompt()
            .user(body.prompt())
            .call()
            .content();

        ChatResponseDTO response = new ChatResponseDTO(answer);
        
        return ResponseEntity.ok(response);
    }
}
