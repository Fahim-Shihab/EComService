package net.springboot.productOrder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.springboot.common.model.GenericModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_info")
public class OrderInfo extends GenericModel implements Serializable {

    @Id
    @Column(name = "order_id", unique=true, nullable = false)
    private String orderId;

    @Column(name = "order_by", nullable = false)
    private long orderBy;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "order_status", length = 1)
    private int orderStatus;

    @Column(name = "customer_comments")
    private String customerComments;

    @Column(name = "admin_comments")
    private String adminComments;

    @Column(name = "expected_delivery_date")
    private Date expectedDeliveryDate;

    @Column(name = "completed_delivery_date")
    private Date completedDeliveryDate;
}
