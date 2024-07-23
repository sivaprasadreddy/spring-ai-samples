package com.sivalabs.springai.showcase.catalog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service("productAvailabilityUpdateFn")
@Transactional
@Description("Update the product availability for a given product code")
@RequiredArgsConstructor
@Slf4j
class ProductAvailabilityUpdateFn implements
        Function<ProductAvailabilityUpdateFn.Request, ProductAvailabilityUpdateFn.Response> {
    private final ProductRepository productRepository;

    @Override
    public Response apply(Request request) {
        log.info("Update the product availability for code: {}", request.code());
        String status = productRepository.findByCode(request.code())
                .map(product -> {
                    product.setAvailable(request.available());
                    productRepository.saveAndFlush(product);
                    return "Product availability updated";
                }).orElse("Product not found");
        return new Response(status);
    }

    public record Request(String code, boolean available) {}
    public record Response(String status) {}
}
