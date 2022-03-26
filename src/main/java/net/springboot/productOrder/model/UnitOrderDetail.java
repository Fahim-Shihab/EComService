package net.springboot.productOrder.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class UnitOrderDetail {

    private String productId;
    private long amount;
    private long price;
    private String discountCode;

}
