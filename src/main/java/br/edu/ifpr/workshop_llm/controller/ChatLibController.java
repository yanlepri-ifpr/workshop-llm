package br.edu.ifpr.workshop_llm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpr.workshop_llm.dto.ChatRequestDTO;
import br.edu.ifpr.workshop_llm.dto.ChatResponseDTO;


@RestController
@RequestMapping("/chat/lib")
public class ChatLibController {
    public ChatLibController() {}

    @PostMapping("")
    public ResponseEntity<ChatResponseDTO> prompt(@RequestBody ChatRequestDTO body) {}
}
