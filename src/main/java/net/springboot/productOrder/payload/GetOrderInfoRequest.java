package net.springboot.productOrder.payload;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.base.Page;
import net.springboot.common.enums.OrderStatus;
import net.springboot.common.enums.Status;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class GetOrderInfoRequest extends Page implements Serializable {
    String orderId;
    long orderBy;
    Date orderDate;
    OrderStatus orderStatus;
    Status status;
}
