package net.springboot.productOrder.dao;

import net.springboot.common.base.ServiceResponse;
import net.springboot.productOrder.payload.GetOrderInfoRequest;
import net.springboot.productOrder.payload.GetOrderInfoResponse;
import net.springboot.productOrder.payload.SaveOrderDetailRequest;

public interface ProductOrderDao {
    ServiceResponse SaveProductOrder(SaveOrderDetailRequest request);
    GetOrderInfoResponse GetProductOrder(GetOrderInfoRequest request);
}
