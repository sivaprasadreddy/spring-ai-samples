package com.sivalabs.aidemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
class MovieController {
    private final ChatClient chatClient;

    MovieController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    private static final String PROMPT_TEMPLATE = """
        What are the best movies directed by {director}?
        
        {format}
        """;
    @GetMapping("/ai/chat/movies")
    DirectorResponse chat(@RequestParam String director) {
        var outputConverter = new BeanOutputConverter<>(DirectorResponse.class);
        var userPromptTemplate = new PromptTemplate(PROMPT_TEMPLATE);
        var prompt = userPromptTemplate.create(Map.of("director", director, "format", outputConverter.getFormat()));
        var response = chatClient.prompt(prompt).call().content();
        return outputConverter.convert(response);
    }

    @GetMapping("/ai/chat/movies-as-map")
    Map<String, Object> chatWithMapOutput(@RequestParam String director) {
        var outputConverter = new MapOutputConverter();
        var userPromptTemplate = new PromptTemplate(PROMPT_TEMPLATE);
        var prompt = userPromptTemplate.create(Map.of("director", director, "format", outputConverter.getFormat()));
        var response = chatClient.prompt(prompt).call().content();
        return outputConverter.convert(response);
    }

    @GetMapping("/ai/chat/movies-as-list")
    List<String> chatWithListOutput(@RequestParam String director) {
        var outputConverter = new ListOutputConverter(new DefaultConversionService());
        var userPromptTemplate = new PromptTemplate(PROMPT_TEMPLATE);
        var prompt = userPromptTemplate.create(Map.of("director", director, "format", outputConverter.getFormat()));
        var response = chatClient.prompt(prompt).call().content();
        return outputConverter.convert(response);
    }
}

record DirectorResponse(String director, List<String> movies) {}
