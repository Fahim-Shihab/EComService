package net.springboot.product.payload.productType;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.base.ServiceResponse;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetProductTypeResponse extends ServiceResponse implements Serializable {
    private List<ProductTypeDto> productTypeList;

    public GetProductTypeResponse(){
        productTypeList = new ArrayList<>();
    }

    public GetProductTypeResponse(String message) {
        super(false, message);
    }

    BigInteger total;
}
