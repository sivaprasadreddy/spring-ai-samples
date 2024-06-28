package com.sivalabs.springai.showcase.catalog;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@Slf4j
class ChatSupportController {
    private final ChatClient chatClient;

    ChatSupportController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    String chat() {
        return "chat";
    }

    @HxRequest
    @PostMapping("/chat")
    String sendMessage(@RequestParam("question") String question,
                       Model model){
        log.info("Received question: {}", question);
        UserMessage userMessage = new UserMessage(question);
        ChatResponse response = chatClient.prompt(new Prompt(
                List.of(userMessage),
                OpenAiChatOptions.builder()
                        .withFunctions(Set.of(
                            "productAvailabilityEnquiryFn",
                            "productAvailabilityUpdateFn")
                        )
                        .build())).call().chatResponse();

        String answer = response.getResult().getOutput().getContent();
        log.info("Answer: {}", answer);
        model.addAttribute("answer", answer);
        return "partials/chat-answer";
    }
}
