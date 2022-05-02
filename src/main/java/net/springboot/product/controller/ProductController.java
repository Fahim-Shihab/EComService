package net.springboot.product.controller;

import net.springboot.common.base.ServiceResponse;
import net.springboot.product.payload.product.GetProductRequest;
import net.springboot.product.payload.product.GetProductResponse;
import net.springboot.product.payload.product.SaveProductRequest;
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

    @PostMapping("/save")
    public @ResponseBody
    ServiceResponse SaveProduct(@RequestBody SaveProductRequest request){
        return productService.SaveProduct(request);
    }

}
