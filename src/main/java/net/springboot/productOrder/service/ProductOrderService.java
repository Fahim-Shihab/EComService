package net.springboot.productOrder.service;

import net.springboot.common.base.ServiceResponse;
import net.springboot.productOrder.payload.GetOrderInfoRequest;
import net.springboot.productOrder.payload.GetOrderInfoResponse;
import net.springboot.productOrder.payload.OrderDetailSaveRequest;
import net.springboot.productOrder.dao.ProductOrderDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderService {
    private final ProductOrderDaoImpl productOrderRepository;

    public ProductOrderService(ProductOrderDaoImpl productOrderRepository)
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
