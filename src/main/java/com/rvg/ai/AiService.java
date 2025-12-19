package com.rvg.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Service class for interacting with AI chat models.
 */
@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Sends a prompt to the AI chat model and returns the response.
     *
     * @param prompt The prompt to send to the AI.
     * @return The AI's response as a String.
     */
    public String chat(String prompt) {
        try {
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            throw new RuntimeException("Error during AI chat interaction", e);
        }
    }
}
