package com.sivalabs.springai.showcase.images;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
class ImageController {
    private final ImageModel imageModel;

    ImageController(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @GetMapping("/generate-image")
    String generateImageForm() {
        return "generate-image";
    }

    @PostMapping("/generate-image")
    @HxRequest
    String generateImage(@RequestParam String instructions, Model model) {
        log.info("Generating Image with Instructions: {}", instructions);
        var url = this.generate(instructions);
        log.info("Generated image URL: {}", url);
        model.addAttribute("url", url);
        return "partials/image";
    }

    String generate(String instructions) {
        var options = ImageOptionsBuilder.builder()
                .withHeight(1024).withWidth(1024)
                .withResponseFormat("url")
                .withModel("dall-e-3")
                .build();
        ImagePrompt imagePrompt = new ImagePrompt(instructions, options);
        ImageResponse imageResponse = imageModel.call(imagePrompt);
        return imageResponse.getResult().getOutput().getUrl();
    }
}
