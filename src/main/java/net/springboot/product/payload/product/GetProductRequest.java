package net.springboot.product.payload.product;

import net.springboot.common.base.Page;
import net.springboot.common.enums.Status;

import java.io.Serializable;

public class GetProductRequest  extends Page implements Serializable {
    String id;
    String name;
    long productType;
    long vendorId;
    String unitPrice;
    String purchaseDate;
    String manufactureDate;
    String expiryDate;
    String discountCode;
    Status status;
}
