package com.sivalabs.chatopenai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestChatOpenaiApplication {

    public static void main(String[] args) {
        SpringApplication.from(ChatOpenaiApplication::main).with(TestChatOpenaiApplication.class).run(args);
    }

}
