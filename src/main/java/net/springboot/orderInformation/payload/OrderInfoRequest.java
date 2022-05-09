package net.springboot.orderInformation.payload;

import lombok.Getter;
import lombok.Setter;

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
    private String orderStatus;
    private String customerComments;
    private String adminComments;
    private Date expectedDeliveryDate;
    private Date completedDeliveryDate;
}
