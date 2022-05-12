package net.springboot.product.dao;

import net.springboot.common.base.ServiceResponse;
import net.springboot.product.payload.product.GetProductRequest;
import net.springboot.product.payload.product.GetProductResponse;
import net.springboot.product.payload.product.SaveProductRequest;

public interface ProductDao {
    ServiceResponse SaveProduct(SaveProductRequest request);
    GetProductResponse GetProduct(GetProductRequest request);
}
