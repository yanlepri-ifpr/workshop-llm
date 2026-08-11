package br.edu.ifpr.workshop_llm.dto;

import java.util.List;

public record APIResponseDTO(
        String id,
        String status,
        Usage usage,
        String created,
        String updated,
        String serviceTier,
        List<Step> steps,
        String object,
        String model
) {

    public record Usage(
            int totalTokens,
            int totalInputTokens,
            List<InputTokensByModality> inputTokensByModality,
            int totalCachedTokens,
            int totalOutputTokens,
            int totalToolUseTokens,
            int totalThoughtTokens,
            int rawPromptToken
    ) {}

    public record InputTokensByModality(
            String modality,
            int tokens
    ) {}

    public record Step(
            String signature,
            String type,
            List<Content> content
    ) {}

    public record Content(
            String text,
            String type
    ) {}
}