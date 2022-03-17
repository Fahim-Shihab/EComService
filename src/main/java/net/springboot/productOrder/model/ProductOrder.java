package net.springboot.productOrder.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_order")

@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)

public class ProductOrder implements Serializable {

    @Id
    @Column(name = "id", unique=true, nullable=false)
    private String id;

    @Type(type="jsonb")
    @Column(name = "unit_order_details", columnDefinition = "jsonb")
    private List<UnitOrderDetail> unitOrderDetails;

    @Column(name = "order_by", nullable = false)
    private long orderBy;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "status")
    private long orderStatus;

    @Column(name = "expected_delivery_date")
    private Date expectedDeliveryDate;

    @Column(name = "completed_delivery_date")
    private Date completedDeliveryDate;

    @Column(name="entry_date")
    private Date entryDate;

    @Column(name="upd_date")
    private Date updDate;

}
