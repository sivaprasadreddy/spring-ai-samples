package com.sivalabs.chatopenai;

import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Service;

@Service
class ImageService {
    private final ImageClient imageClient;

    ImageService(ImageClient imageClient) {
        this.imageClient = imageClient;
    }

    String generate(String instructions) {
        var options = ImageOptionsBuilder.builder()
                .withHeight(1024).withWidth(1024)
                .withModel("dall-e-3")
                .build();
        ImagePrompt imagePrompt = new ImagePrompt(instructions, options);
        ImageResponse imageResponse = imageClient.call(imagePrompt);
        return imageResponse.getResult().getOutput().getUrl();
    }
}
