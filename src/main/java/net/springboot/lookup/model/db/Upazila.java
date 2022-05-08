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
@Table(name = "upazila")
public class Upazila extends GenericModel implements Serializable {
    @Id
    @Column(name="id")
    private int upazilaId;

    @Column(name = "upazila_name")
    private String upazilaName;

    @JoinColumn(name = "district_id", referencedColumnName = "id")
    District districtId;

    public Upazila(Upazila upazila) {
        this.upazilaName = upazila.getUpazilaName();
        this.upazilaId = upazila.getUpazilaId();
        this.districtId = upazila.getDistrictId();
        this.setEntryById(upazila.getEntryById());
        if (upazila.getEntryDate() != null) {
            this.setEntryDate(new Date(upazila.getEntryDate().getTime()));
        } else {
            this.setEntryDate(new Date(Calendar.getInstance().getTime().getTime()));
        }
        this.setUpdById(upazila.getUpdById());
        if (upazila.getUpdDate() != null) {
            this.setUpdDate(new Date(upazila.getUpdDate().getTime()));
        }
    }
}
