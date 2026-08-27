package br.edu.ifpr.workshop_llm.controller;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpr.workshop_llm.dto.ChatRequestDTO;
import br.edu.ifpr.workshop_llm.dto.ChatResponseDTO;


@RestController
@RequestMapping("/chat/lib")
public class ChatLibController {
    private ChatClient chatClient;

    private ChatMemory memory;

    private String CONVERSATION_ID = "42";

    public ChatLibController(
        ChatClient.Builder builder,
        ChatMemory memory 
    ) {
        this.memory = memory;
        this.chatClient = builder
        .defaultAdvisors(
            MessageChatMemoryAdvisor.builder(memory).build()
        )
        .build();
    }

    @PostMapping("")
    public ResponseEntity<ChatResponseDTO> prompt(@RequestBody ChatRequestDTO body) {
        String answer = chatClient.prompt()
            .user(body.prompt())
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, CONVERSATION_ID))
            .call()
            .content();

        ChatResponseDTO response = new ChatResponseDTO(answer);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Message>> getHistory() {
        return ResponseEntity.ok(memory.get(CONVERSATION_ID));
    }
}
