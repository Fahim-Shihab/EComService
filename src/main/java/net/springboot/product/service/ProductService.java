package net.springboot.product.service;

import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.Utils;
import net.springboot.product.payload.GetProductTypeRequest;
import net.springboot.product.payload.GetProductTypeResponse;
import net.springboot.product.payload.SaveProductTypeRequest;
import net.springboot.product.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public ServiceResponse SaveProductType(SaveProductTypeRequest request) {

        if (!Utils.isOk(request.getCategory())){
            return new ServiceResponse(false,"Product Type category is required");
        }

        return productRepository.SaveProductType(request);
    }

    public GetProductTypeResponse GetProductType(GetProductTypeRequest request) {
        return productRepository.GetProductType(request);
    }
}
