package net.springboot.productOrder.dao;

import net.springboot.common.base.ServiceResponse;
import net.springboot.productOrder.payload.*;

public interface ProductOrderDao {
    ServiceResponse SaveProductOrderDetail(SaveOrderDetailRequest request);
    GetOrderInfoResponse GetProductOrderInfo(GetOrderInfoRequest request);
    GetProductOrderResponse GetProductOrderDetail(GetProductOrderRequest request);
}
