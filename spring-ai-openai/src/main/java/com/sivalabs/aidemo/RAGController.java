package com.sivalabs.aidemo;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.parser.MapOutputParser;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
class RAGController {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    RAGController(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/ai/rag/people")
    Person chatWithRag(@RequestParam String name) {
        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.query(name).withTopK(2));
        String information = similarDocuments.stream().map(Document::getContent).collect(Collectors.joining(System.lineSeparator()));

        var systemPromptTemplate = new SystemPromptTemplate("""
              You are a helpful assistant.
              
              Use the following information to answer the question:
              {information}
              """);
        var systemMessage = systemPromptTemplate.createMessage(
                Map.of("information", information));

        var outputParser = new BeanOutputParser<>(Person.class);
        PromptTemplate userMessagePromptTemplate = new PromptTemplate("""
        Tell me about {name} as if current date is {current_date}.

        {format}
        """);
        Map<String,Object> model = Map.of("name", name,
                "current_date", LocalDate.now(),
                "format", outputParser.getFormat());
        var userMessage = new UserMessage(userMessagePromptTemplate.create(model).getContents());

        var prompt = new Prompt(List.of(systemMessage, userMessage));

        var response = chatClient.call(prompt).getResult().getOutput().getContent();

        return outputParser.parse(response);
    }
}

record Person(String name,
              String dateOfBirth,
              int experienceInYears,
              List<String> books) {
}