package com.sivalabs.aidemo;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.parser.ListOutputParser;
import org.springframework.ai.parser.MapOutputParser;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
class MovieController {
    private final ChatClient chatClient;

    MovieController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    private static final String PROMPT_TEMPLATE = """
        What are the best movies directed by {director}?
        
        {format}
        """;
    @GetMapping("/ai/chat/movies")
    DirectorResponse chat(@RequestParam String director) {
        var outputParser = new BeanOutputParser<>(DirectorResponse.class);
        var userPromptTemplate = new PromptTemplate(PROMPT_TEMPLATE);
        var prompt = userPromptTemplate.create(Map.of("director", director, "format", outputParser.getFormat()));
        var response = chatClient.call(prompt);
        return outputParser.parse(response.getResult().getOutput().getContent());
    }

    @GetMapping("/ai/chat/movies-as-map")
    Map<String, Object> chatWithMapOutput(@RequestParam String director) {
        var outputParser = new MapOutputParser();
        var userPromptTemplate = new PromptTemplate(PROMPT_TEMPLATE);
        var prompt = userPromptTemplate.create(Map.of("director", director, "format", outputParser.getFormat()));
        var response = chatClient.call(prompt);
        return outputParser.parse(response.getResult().getOutput().getContent());
    }

    @GetMapping("/ai/chat/movies-as-list")
    List<String> chatWithListOutput(@RequestParam String director) {
        var outputParser = new ListOutputParser(new DefaultConversionService());
        var userPromptTemplate = new PromptTemplate(PROMPT_TEMPLATE);
        var prompt = userPromptTemplate.create(Map.of("director", director, "format", outputParser.getFormat()));
        var response = chatClient.call(prompt);
        return outputParser.parse(response.getResult().getOutput().getContent());
    }
}

record DirectorResponse(String director, List<String> movies) {}
