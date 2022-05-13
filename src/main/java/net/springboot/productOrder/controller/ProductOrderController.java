package net.springboot.productOrder.controller;

import net.springboot.common.base.ServiceResponse;
import net.springboot.productOrder.payload.*;
import net.springboot.productOrder.service.ProductOrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productOrder")
public class ProductOrderController {
    private final ProductOrderService productOrderService;

    public ProductOrderController(ProductOrderService productOrderService)
    {
        this.productOrderService = productOrderService;
    }

    @PostMapping("/saveOrderDetail")
    public @ResponseBody
    ServiceResponse SaveProductOrderDetail(@RequestBody SaveOrderDetailRequest request){
        return productOrderService.SaveProductOrderDetail(request);
    }

    @PostMapping("/getOrderInfo")
    public @ResponseBody
    GetOrderInfoResponse GetProductOrderInfo(@RequestBody GetOrderInfoRequest request){
        return productOrderService.GetProductOrderInfo(request);
    }

    @PostMapping("/getOrderDetail")
    public @ResponseBody
    GetProductOrderResponse GetProductOrderDetail(@RequestBody GetProductOrderRequest request){
        return productOrderService.GetProductOrderDetail(request);
    }
}
