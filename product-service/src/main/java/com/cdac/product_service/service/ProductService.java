package com.cdac.product_service.service;

import com.cdac.product_service.dto.ProductRequest;
import com.cdac.product_service.dto.ProductResponse;
import com.cdac.product_service.model.Product;
import com.cdac.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@Slf4j
public class ProductService {
    @Autowired
    private  ProductRepository productRepository;


    public  void createProduct(ProductRequest request){
        Product product= Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} saved", product.getId());
    }

    public List<ProductResponse>getAllProducts(){
        List<Product>products= productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }
    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name((product.getName()))
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }
}
