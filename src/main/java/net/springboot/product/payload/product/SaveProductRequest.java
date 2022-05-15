package net.springboot.product.payload.product;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.enums.Status;
import net.springboot.product.model.ProductCategory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SaveProductRequest implements Serializable {
    String id;
    String name;
    long type;
    List<ProductCategory> details;
    String description;
    long vendorId;
    byte[] photo;
    long amount;
    double purchaseCost;
    double unitPrice;
    Date purchaseDate;
    Date manufactureDate;
    Date expiryDate;
    String discountCode;
    Status status;
}
