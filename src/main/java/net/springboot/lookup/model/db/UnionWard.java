package net.springboot.lookup.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.springboot.common.model.GenericModel;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "union_ward")
public class UnionWard extends GenericModel implements Serializable {
    @Id
    @Column(name="id")
    private int unionWardId;

    @Column(name = "union_ward_name")
    private String unionWardName;

    @JoinColumn(name = "district_id", referencedColumnName = "id")
    Upazila upazilaId;

    public UnionWard(UnionWard unionWard) {
        this.unionWardId = unionWard.getUnionWardId();
        this.unionWardName = unionWard.getUnionWardName();
        this.upazilaId = unionWard.getUpazilaId();
        this.setEntryById(unionWard.getEntryById());
        if (unionWard.getEntryDate() != null) {
            this.setEntryDate(new Date(unionWard.getEntryDate().getTime()));
        } else {
            this.setEntryDate(new Date(Calendar.getInstance().getTime().getTime()));
        }
        this.setUpdById(unionWard.getUpdById());
        if (unionWard.getUpdDate() != null) {
            this.setUpdDate(new Date(unionWard.getUpdDate().getTime()));
        }
    }
}
