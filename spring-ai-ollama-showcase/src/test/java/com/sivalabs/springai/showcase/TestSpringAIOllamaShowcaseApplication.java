package com.sivalabs.springai.showcase;

import org.springframework.boot.SpringApplication;

public class TestSpringAIOllamaShowcaseApplication {

    public static void main(String[] args) {
        SpringApplication
                .from(SpringAIOllamaShowcaseApplication::main)
                .with(ContainersConfig.class).run(args);
    }
}
