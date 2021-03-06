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
    String vendorName;
    String photo;
    long amount;
    double purchaseCost;
    double unitPrice;
    String purchaseDate;
    String manufactureDate;
    String expiryDate;
    String discountCode;
    String status;
}
