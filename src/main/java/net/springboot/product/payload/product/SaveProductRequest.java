package net.springboot.product.payload.product;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.enums.Status;

import java.io.Serializable;

@Getter
@Setter
public class SaveProductRequest implements Serializable {
    String id;
    String name;
    long productType;
    long vendorId;
    String photo;
    long amount;
    double purchaseCost;
    double unitPrice;
    String purchaseDate;
    String manufactureDate;
    String expiryDate;
    String discountCode;
    Status status;
}
