package net.springboot.productOrder.service;

import net.springboot.common.base.ServiceResponse;
import net.springboot.productOrder.payload.ProductOrderRequest;
import net.springboot.productOrder.repository.ProductOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository)
    {
        this.productOrderRepository = productOrderRepository;
    }

    public ServiceResponse SaveProductOrder(List<ProductOrderRequest> request){
        return productOrderRepository.SaveProductOrder(request);
    }

//    public GetProductResponse GetProduct(GetProductRequest request){
//        return productRepository.GetProduct(request);
//    }
}
