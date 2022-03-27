package net.springboot.product.service;

import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.Utils;
import net.springboot.product.payload.product.SaveProductRequest;
import net.springboot.product.payload.productType.GetProductTypeRequest;
import net.springboot.product.payload.productType.GetProductTypeResponse;
import net.springboot.product.payload.productType.SaveProductTypeRequest;
import net.springboot.product.repository.ProductRepository;
import net.springboot.product.repository.ProductTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;

    public ProductService(ProductTypeRepository productTypeRepository, ProductRepository productRepository)
    {
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
    }

    public ServiceResponse SaveProductType(SaveProductTypeRequest request) {

        if (!Utils.isOk(request.getCategory())){
            return new ServiceResponse(false,"Product Type category is required");
        }

        return productTypeRepository.SaveProductType(request);
    }

    public GetProductTypeResponse GetProductType(GetProductTypeRequest request) {
        return productTypeRepository.GetProductType(request);
    }

    public ServiceResponse SaveProduct(SaveProductRequest request){
        return productRepository.SaveProduct(request);
    }
}
