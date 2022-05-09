package net.springboot.productOrder.payload;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ProductOrderRequest implements Serializable {
    private String id;
    private String productId;
    private long amount;
    private long price;
    private String discountCode;
    private String orderId;
    private String orderStatus;
    private String customerComments;
    private String adminComments;
    private Date returnDate;
}