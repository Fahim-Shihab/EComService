package net.springboot.productOrder.payload;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.enums.OrderStatus;
import net.springboot.common.enums.Status;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class OrderInfoRequest implements Serializable {
    private String orderId;
    private long orderBy;
    private Date orderDate;
    private double totalPrice;
    private String deliveryAddress;
    private OrderStatus orderStatus;
    private Status status;
    private String customerComments;
    private String adminComments;
    private Date expectedDeliveryDate;
    private Date completedDeliveryDate;
}
