package com.sivalabs.springai.showcase.catalog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service("productAvailabilityEnquiryFn")
@Description("Get the product availability for a given product code")
@RequiredArgsConstructor
@Slf4j
class ProductAvailabilityEnquiryFn implements
        Function<ProductAvailabilityEnquiryFn.Request, ProductAvailabilityEnquiryFn.Response> {
    private final ProductRepository productRepository;

    @Override
    public Response apply(Request request) {
        log.info("Get product status for code: {}", request.code());
        String status = productRepository.findByCode(request.code())
                .map(o -> o.isAvailable()? "Available": "Unavailable")
                .orElse("UNKNOWN");
        return new Response(status);
    }

    public record Request(String code) {}
    public record Response(String status) {}
}
