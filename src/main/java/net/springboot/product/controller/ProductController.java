package net.springboot.product.controller;

import net.springboot.common.base.ServiceResponse;
import net.springboot.product.payload.product.GetProductRequest;
import net.springboot.product.payload.product.GetProductResponse;
import net.springboot.product.payload.product.SaveProductRequest;
import net.springboot.product.payload.productType.GetProductTypeRequest;
import net.springboot.product.payload.productType.GetProductTypeResponse;
import net.springboot.product.payload.productType.SaveProductTypeRequest;
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

    @PostMapping("/save")
    public @ResponseBody
    ServiceResponse SaveProduct(@RequestBody SaveProductRequest request){
        return productService.SaveProduct(request);
    }

}
