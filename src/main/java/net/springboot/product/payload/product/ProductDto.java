package net.springboot.product.payload.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    String id;
    String name;
    long productType;
    long vendorId;
    String photo;
    long amount;
    double purchaseCost;
    String unitPrice;
    String purchaseDate;
    String manufactureDate;
    String expiryDate;
    String discountCode;
    String status;
}
