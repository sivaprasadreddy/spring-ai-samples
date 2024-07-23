package com.sivalabs.springai.showcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringAIShowcaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAIShowcaseApplication.class, args);
    }

}
