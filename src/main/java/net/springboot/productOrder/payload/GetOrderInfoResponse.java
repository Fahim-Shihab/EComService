package net.springboot.productOrder.payload;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.base.ServiceResponse;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class GetOrderInfoResponse extends ServiceResponse implements Serializable {
    List<OrderInfoRequest> orderInfoDetailsList;
    public GetOrderInfoResponse(String message) {
        super(false, message);
    }
    public GetOrderInfoResponse(){
        orderInfoDetailsList = Collections.emptyList();
    }
    BigInteger total;
}
