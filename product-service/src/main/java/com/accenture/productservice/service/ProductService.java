package com.accenture.productservice.service;

import com.accenture.productservice.dto.ProductRequest;
import com.accenture.productservice.dto.ProductResponse;
import com.accenture.productservice.model.Product;
import com.accenture.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest) {
    Product product = Product.builder()
            .name(productRequest.getName())
            .description(productRequest.getDescription())
            .price(productRequest.getPrice())
            .createdAt(LocalDateTime.now())
                    .build();

    productRepository.save(product);
    log.info("Product with ID: {} was saved to database.", product.getId());
}

public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().
                stream()
                .map(this::mapToProductResponse)
                .toList();
}

private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .build();
}
}
