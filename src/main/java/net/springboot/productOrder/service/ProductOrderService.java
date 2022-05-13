package net.springboot.productOrder.service;

import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.Utils;
import net.springboot.productOrder.payload.*;
import net.springboot.productOrder.dao.ProductOrderDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderService {
    private final ProductOrderDaoImpl productOrderDao;

    public ProductOrderService(ProductOrderDaoImpl productOrderDao)
    {
        this.productOrderDao = productOrderDao;
    }

    public ServiceResponse SaveProductOrderDetail(SaveOrderDetailRequest request){
        return productOrderDao.SaveProductOrderDetail(request);
    }

    public GetOrderInfoResponse GetProductOrderInfo(GetOrderInfoRequest request){
        return productOrderDao.GetProductOrderInfo(request);
    }

    public GetProductOrderResponse GetProductOrderDetail(GetProductOrderRequest request){
        if (!Utils.isOk(request.getOrderId())){
            return new GetProductOrderResponse("Order Id is required");
        }
        return productOrderDao.GetProductOrderDetail(request);
    }
}
