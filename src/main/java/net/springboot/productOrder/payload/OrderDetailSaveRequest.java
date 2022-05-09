package net.springboot.productOrder.payload;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OrderDetailSaveRequest implements Serializable {
    List<ProductOrderRequest> productOrderList;
    OrderInfoRequest orderInfo;
}
