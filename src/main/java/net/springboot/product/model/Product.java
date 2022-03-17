package net.springboot.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.springboot.productDescription.model.ProductDescription;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @Column(name = "id", unique=true, nullable=false)
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_description_id", referencedColumnName = "id")
    ProductDescription productDescriptionId;

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

    @Column(name="entry_date")
    private Date entryDate;

    @Column(name="upd_date")
    private Date updDate;

    @Column(name = "entry_by")
    private long entryById;

    @Column(name = "update_by")
    private long updById;
}
