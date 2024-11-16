package com.sivalabs.aidemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Disabled
class ApplicationTests {

    @Autowired
    ImageService imageService;

    @Test
    void generateImage() {
        String url = imageService.generate("Two modern robots talking to each other in a room");
        assertThat(url).isNotNull();
        System.out.println(url);
    }

}
