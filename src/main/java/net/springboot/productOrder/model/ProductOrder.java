package net.springboot.productOrder.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.springboot.common.model.GenericModel;
import net.springboot.product.model.Product;
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

public class ProductOrder extends GenericModel implements Serializable {

    @Id
    @Column(name = "id", unique=true, nullable=false)
    private String id;

//    @Type(type="jsonb")
//    @Column(name = "unit_order_details", columnDefinition = "jsonb")
//    private List<UnitOrderDetail> unitOrderDetails;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product productId;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "order_status", length = 1)
    private int orderStatus;

    @Column(name = "customer_comments")
    private String customerComments;

    @Column(name = "admin_comments")
    private String adminComments;

    @Column(name = "return_date")
    private Date returnDate;

}
