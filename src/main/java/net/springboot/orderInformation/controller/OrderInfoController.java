package net.springboot.orderInformation.controller;

import net.springboot.orderInformation.service.OrderInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderInfo")
public class OrderInfoController {
    private final OrderInfoService orderInfoService;

    public OrderInfoController(OrderInfoService orderInfoService)
    {
        this.orderInfoService = orderInfoService;
    }
}
