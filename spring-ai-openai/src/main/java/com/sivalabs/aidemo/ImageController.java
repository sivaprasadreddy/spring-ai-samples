package com.sivalabs.aidemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
class ImageController {
    private final ImageService imageService;

    ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/ai/image")
    Map<String, String> generateImage(@RequestParam String instructions) {
        var url = imageService.generate(instructions);
        return Map.of("url", url);
    }
}
