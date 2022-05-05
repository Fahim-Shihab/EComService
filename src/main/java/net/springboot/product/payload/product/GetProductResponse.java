package net.springboot.product.payload.product;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.base.ServiceResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetProductResponse extends ServiceResponse {
    private List<ProductDto> productList;

    public GetProductResponse(){
        productList = new ArrayList<>();
    }

    public GetProductResponse(String message) {
        super(false, message);
    }

    BigInteger total;
}
