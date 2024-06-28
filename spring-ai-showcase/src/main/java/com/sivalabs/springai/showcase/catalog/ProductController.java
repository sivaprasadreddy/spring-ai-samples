package com.sivalabs.springai.showcase.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
class ProductController {
    private final ProductRepository repo;

    @GetMapping
    String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    String showProducts(Model model) {
        List<Product> productsPage = repo.findAll();
        model.addAttribute("productsPage", productsPage);
        return "products";
    }
}
