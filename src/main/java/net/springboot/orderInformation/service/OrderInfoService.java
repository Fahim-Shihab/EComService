package net.springboot.orderInformation.service;

import net.springboot.common.base.ServiceResponse;
import net.springboot.orderInformation.payload.OrderInfoRequest;
import net.springboot.orderInformation.repository.OrderInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoService {
    private final OrderInfoRepository orderInfoRepository;

    public OrderInfoService(OrderInfoRepository orderInfoRepository)
    {
        this.orderInfoRepository = orderInfoRepository;
    }

    public ServiceResponse SaveProductOrder(OrderInfoRequest request){
        return orderInfoRepository.SaveOrderInfo(request);
    }
}
