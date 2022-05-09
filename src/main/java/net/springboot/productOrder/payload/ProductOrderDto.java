package net.springboot.productOrder.payload;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductOrderDto implements Serializable {
    private String id;
    private String productId;
    private String productName;
    private long amount;
    private long price;
    private String discountCode;
    private String orderId;
    private String orderStatus;
    private String customerComments;
    private String adminComments;
}
