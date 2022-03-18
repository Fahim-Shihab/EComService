package net.springboot.vendor.model;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vendor")

@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        ),
        @TypeDef(
                name = "int-array",
                typeClass = IntArrayType.class
        )
})

@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)

public class Vendor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Type(type="jsonb")
    @Column(name = "contact_info", columnDefinition = "jsonb")
    private List<VendorContactInfo> vendorContactInfos;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name="entry_date")
    private Date entryDate;

    @Column(name="upd_date")
    private Date updDate;

    @Column(name = "entry_by")
    private long entryById;

    @Column(name = "update_by")
    private long updById;

}
