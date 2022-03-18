package net.springboot.productDescription.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.springboot.vendor.model.Vendor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_description")

public class ProductDescription implements Serializable {

    @Id
    @Column(name = "id", unique=true, nullable=false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "product_type_id")
    private long productTypeId;

    @Column(name = "vendor_id")
    private long vendorId;

    @Column(name = "photo")
    private String photo;

    @Column(name="entry_date")
    private Date entryDate;

    @Column(name="upd_date")
    private Date updDate;

    @Column(name = "entry_by")
    private long entryById;

    @Column(name = "update_by")
    private long updById;

}
