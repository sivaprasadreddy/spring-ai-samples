package com.sivalabs.springai.showcase;

import org.springframework.boot.SpringApplication;

public class TestSpringAIShowcaseApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringAIShowcaseApplication::main)
                .with(ContainersConfig.class).run(args);
    }
}
