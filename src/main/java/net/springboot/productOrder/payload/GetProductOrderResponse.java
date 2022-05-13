package net.springboot.productOrder.payload;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.base.ServiceResponse;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class GetProductOrderResponse extends ServiceResponse implements Serializable {
    List<ProductOrderDto> productOrderList;
    public GetProductOrderResponse(String message) {
        super(false, message);
    }
    public GetProductOrderResponse(){
        productOrderList = Collections.emptyList();
    }
    BigInteger total;
}
