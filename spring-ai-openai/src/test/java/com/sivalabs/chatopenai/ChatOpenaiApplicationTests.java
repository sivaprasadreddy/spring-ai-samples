package com.sivalabs.chatopenai;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Disabled
class ChatOpenaiApplicationTests {

    @Autowired
    ImageService imageService;

    @Test
    void generateImage() {
        String url = imageService.generate("Two modern robots talking to each other in a room.");
        System.out.println(url);
    }

}
