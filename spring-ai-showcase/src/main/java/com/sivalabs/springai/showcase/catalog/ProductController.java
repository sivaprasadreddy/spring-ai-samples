package com.sivalabs.springai.showcase.catalog;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/fn-calling")
class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository repo;
    private final ChatClient chatClient;

    ProductController(ProductRepository repo,
                      ChatClient.Builder chatClientBuilder) {
        this.repo = repo;
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("")
    String showProducts(Model model) {
        model.addAttribute("products", getProducts());
        return "fn-calling/index";
    }

    @HxRequest
    @PostMapping("/chat")
    HtmxResponse sendMessage(@RequestParam("question") String question){
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
        return HtmxResponse.builder()
                .view(new ModelAndView("fn-calling/partial-chat-answer", Map.of("answer", answer)))
                .view(new ModelAndView("fn-calling/partial-products",
                        Map.of("products", getProducts())))
                .build();
    }

    private List<Product> getProducts() {
        Sort sort = Sort.by("code");
        return repo.findAll(sort);
    }
}
