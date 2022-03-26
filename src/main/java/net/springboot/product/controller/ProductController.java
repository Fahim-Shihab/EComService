package net.springboot.product.controller;

import net.springboot.common.base.ServiceResponse;
import net.springboot.product.payload.GetProductTypeRequest;
import net.springboot.product.payload.GetProductTypeResponse;
import net.springboot.product.payload.SaveProductTypeRequest;
import net.springboot.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @PostMapping("/productType/save")
    public @ResponseBody
    ServiceResponse SaveProductType(@RequestBody SaveProductTypeRequest request){
        return productService.SaveProductType(request);
    }

    @PostMapping("/productType/get")
    public @ResponseBody
    GetProductTypeResponse GetProductType(@RequestBody GetProductTypeRequest request){
        return productService.GetProductType(request);
    }

}
