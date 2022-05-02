package net.springboot.product.payload.product;

import net.springboot.common.base.Page;
import net.springboot.common.enums.Status;
import net.springboot.product.model.ProductCategory;

import java.io.Serializable;
import java.util.List;

public class GetProductRequest  extends Page implements Serializable {
    String id;
    String name;
    long type;
    List<ProductCategory> details;
    long vendorId;
    String unitPrice;
    String purchaseDate;
    String manufactureDate;
    String expiryDate;
    String discountCode;
    Status status;
}
