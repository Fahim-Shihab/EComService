package net.springboot.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.springboot.common.model.GenericModel;
import net.springboot.discount.model.Discount;
import net.springboot.vendor.model.Vendor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends GenericModel implements Serializable {

    @Id
    @Column(name = "id", unique=true, nullable=false)
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_type", referencedColumnName = "id")
    ProductType productType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    Vendor vendorId;

    @Column(name = "photo", columnDefinition = "TEXT")
    private String photo;

    @Column(name = "amount")
    private long amount;

    @Column(name="purchase_cost")
    private double purchaseCost;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name="purchase_date")
    private Date purchaseDate;

    @Column(name="manufacture_date")
    private Date manufactureDate;

    @Column(name="expiry_date")
    private Date expiryDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discount_code", referencedColumnName = "code")
    Discount discountCode;

}
