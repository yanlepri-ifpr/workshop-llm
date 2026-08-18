package br.edu.ifpr.workshop_llm.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpr.workshop_llm.dto.ChatRequestDTO;
import br.edu.ifpr.workshop_llm.dto.ChatResponseDTO;
import br.edu.ifpr.workshop_llm.helper.Message;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/chat/lib")
public class ChatLibController {
    private ChatClient chatClient;

    private static List<Message> history = new ArrayList<Message>();

    public ChatLibController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping("")
    public ResponseEntity<ChatResponseDTO> prompt(@RequestBody ChatRequestDTO body) {
        Message userPrompt = new Message("Me", body.prompt(), LocalDateTime.now());

        history.add(userPrompt);

        String answer = chatClient.prompt()
            .user(history.toString())
            .call()
            .content();

        Message llmAnswer = new Message("You", answer, LocalDateTime.now());
        history.add(llmAnswer);

        ChatResponseDTO response = new ChatResponseDTO(answer);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Message>> getHistory() {
        return ResponseEntity.ok(history);
    }
}
