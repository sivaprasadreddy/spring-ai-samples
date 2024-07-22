package com.sivalabs.springai.showcase.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Configuration
class VectorStoreConfig {
    private static final Logger logger = LoggerFactory.getLogger(VectorStoreConfig.class);

    List<String> urls = List.of(
            "https://www.sivalabs.in/spring-boot-best-practices/",
            "https://www.sivalabs.in/mastering-spring-boot-in-5-stages/",
            "https://www.sivalabs.in/spring-boot-jooq-tutorial-getting-started/"
    );
    
    @Bean
    ApplicationRunner intVectorStore(VectorStore vectorStore) {
        return args -> {

            urls.forEach(url -> {
                logger.info("Loading document from {}", url);
                try {
                    load(vectorStore, url);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            });
            
        };
    }

    private static void load(VectorStore vectorStore, String url) throws MalformedURLException {
        var documentUri = URI.create(url);
        var htmlReader = new TikaDocumentReader(new UrlResource(documentUri));
        List<Document> documents = new ArrayList<>(htmlReader.get());

        logger.info("Creating and storing Embeddings from Documents");
        //TODO; Need to tune the splitter
        var textSplitter = new TokenTextSplitter();
        vectorStore.add(textSplitter.split(documents));
    }
}
