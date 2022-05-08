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
@Table(name = "village")
public class Village extends GenericModel implements Serializable {
    @Id
    @Column(name="id")
    private int villageId;

    @Column(name = "village_name")
    private String villageName;

    @JoinColumn(name = "union_ward_id", referencedColumnName = "id")
    UnionWard unionWardId;

    public Village(Village village) {
        this.villageId = village.getVillageId();
        this.villageName = village.getVillageName();
        this.unionWardId = village.getUnionWardId();
        this.setEntryById(village.getEntryById());
        if (village.getEntryDate() != null) {
            this.setEntryDate(new Date(village.getEntryDate().getTime()));
        } else {
            this.setEntryDate(new Date(Calendar.getInstance().getTime().getTime()));
        }
        this.setUpdById(village.getUpdById());
        if (village.getUpdDate() != null) {
            this.setUpdDate(new Date(village.getUpdDate().getTime()));
        }
    }
}
