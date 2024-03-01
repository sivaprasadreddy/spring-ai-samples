package com.sivalabs.aidemo;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
class ChatController {

    private final ChatClient chatClient;

    ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai/chat")
    Map<String,String> chat(@RequestParam String question) {
        var response = chatClient.call(question);
        return Map.of("question", question, "answer", response);
    }

    @GetMapping("/ai/chat-with-prompt")
    JokeResponse chatWithPrompt(@RequestParam String subject) {
        var promptTemplate = new PromptTemplate("Tell me a joke about {subject}");
        var prompt = promptTemplate.create(Map.of("subject", subject));
        var response = chatClient.call(prompt).getResult().getOutput().getContent();
        return new JokeResponse(response);
    }

    @GetMapping("/ai/chat-with-system-prompt")
    JokeResponse chatWithSystemPrompt(@RequestParam String subject) {
        var systemMessage = new SystemMessage("You are a sarcastic and funny chatbot");
        var userMessage = new UserMessage("Tell me a joke about " + subject);
        var prompt = new Prompt(List.of(systemMessage, userMessage));
        var response = chatClient.call(prompt).getResult().getOutput().getContent();
        return new JokeResponse(response);
    }

}

record JokeResponse(String response) {}
