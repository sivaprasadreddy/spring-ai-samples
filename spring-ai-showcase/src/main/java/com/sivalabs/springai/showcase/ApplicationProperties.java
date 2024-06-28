package com.sivalabs.springai.showcase;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "bookstore")
public record ApplicationProperties() {}
