package net.springboot.productOrder.controller;

import net.springboot.common.base.ServiceResponse;
import net.springboot.productOrder.payload.GetOrderInfoRequest;
import net.springboot.productOrder.payload.GetOrderInfoResponse;
import net.springboot.productOrder.payload.OrderDetailSaveRequest;
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

    @PostMapping("/save")
    public @ResponseBody
    ServiceResponse SaveProductOrder(@RequestBody OrderDetailSaveRequest request){
        return productOrderService.SaveProductOrder(request);
    }

    @PostMapping("/get")
    public @ResponseBody
    GetOrderInfoResponse GetProductOrder(@RequestBody GetOrderInfoRequest request){
        return productOrderService.GetProductOrder(request);
    }
}
