package com.sivalabs.springai.showcase.rag;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rag/web-qa")
class WebUrlsRagController {
    private static final Logger log = LoggerFactory.getLogger(WebUrlsRagController.class);

    private final ChatClient chatClient;

    public WebUrlsRagController(ChatClient.Builder builder,
                                VectorStore vectorStore) {
        this.chatClient = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                .build();
    }

    @GetMapping
    String webQA() {
        return "rag/web-qa";
    }

    @HxRequest
    @PostMapping("/ask")
    public String ask(@RequestParam("question") String question, Model model) {
        log.info("Received question: {}", question);
        String answer = chatClient
                .prompt()
                .user(question)
                .call()
                .chatResponse().getResult().getOutput().getContent();
        log.info("Answer: {}", answer);
        model.addAttribute("answer", answer);
        return "rag/partial-chat-answer";
    }
}

