package net.springboot.product.service;

import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.Utils;
import net.springboot.product.payload.product.GetProductRequest;
import net.springboot.product.payload.product.GetProductResponse;
import net.springboot.product.payload.product.SaveProductRequest;
import net.springboot.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public ServiceResponse SaveProduct(SaveProductRequest request){
        return productRepository.SaveProduct(request);
    }

    public GetProductResponse GetProduct(GetProductRequest request){
        return productRepository.GetProduct(request);
    }
}
