package net.springboot.productOrder.service;

import net.springboot.common.base.ServiceResponse;
import net.springboot.productOrder.payload.GetOrderInfoRequest;
import net.springboot.productOrder.payload.GetOrderInfoResponse;
import net.springboot.productOrder.payload.OrderDetailSaveRequest;
import net.springboot.productOrder.repository.ProductOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository)
    {
        this.productOrderRepository = productOrderRepository;
    }

    public ServiceResponse SaveProductOrder(OrderDetailSaveRequest request){
        return productOrderRepository.SaveProductOrder(request);
    }

    public GetOrderInfoResponse GetProductOrder(GetOrderInfoRequest request){
        return productOrderRepository.GetProductOrder(request);
    }
}
