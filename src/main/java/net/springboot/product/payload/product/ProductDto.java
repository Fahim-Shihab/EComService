package net.springboot.product.payload.product;

import lombok.Getter;
import lombok.Setter;
import net.springboot.product.model.ProductCategory;

import java.util.List;

@Getter
@Setter
public class ProductDto {
    String id;
    String name;
    long type;
    List<ProductCategory> details;
    String description;
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
