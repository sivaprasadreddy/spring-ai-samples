package com.sivalabs.springai.showcase;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.chromadb.ChromaDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ollama.OllamaContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {
    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgres() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));
    }

    @Bean
    @ServiceConnection
    OllamaContainer ollama() {
        return new OllamaContainer(DockerImageName.parse("ollama/ollama"));
    }

    @Bean
    @ServiceConnection
    ChromaDBContainer chromaContainer() {
        return new ChromaDBContainer(DockerImageName.parse("chromadb/chroma:latest"));
    }
}
