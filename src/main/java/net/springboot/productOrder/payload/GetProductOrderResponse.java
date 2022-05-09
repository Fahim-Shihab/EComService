package net.springboot.productOrder.payload;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.base.ServiceResponse;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class GetProductOrderResponse extends ServiceResponse implements Serializable {
    List<ProductOrderDto> productOrderDtoList;
    OrderInfoRequest orderInfoDetails;
}
