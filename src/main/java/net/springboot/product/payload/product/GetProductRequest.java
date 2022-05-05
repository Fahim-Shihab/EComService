package net.springboot.product.payload.product;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.base.Page;
import net.springboot.common.enums.Status;
import net.springboot.product.model.ProductCategory;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class GetProductRequest extends Page implements Serializable {
    String id;
    String name;
    long type;
    long vendorId;
    int minPrice;
    int maxPrice;
    String expiryDate;
    String discountCode;
    Status status;
}
