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
@Table(name = "district")
public class District extends GenericModel implements Serializable {

    @Id
    @Column(name="id")
    private int districtId;

    @Column(name = "district_name")
    private String districtName;

    @JoinColumn(name = "division_id", referencedColumnName = "id")
    Division divisionId;

    public District(District district) {
        this.districtId = district.getDistrictId();
        this.districtName = district.getDistrictName();
        this.divisionId = district.getDivisionId();
        this.setEntryById(district.getEntryById());
        if (district.getEntryDate() != null) {
            this.setEntryDate(new Date(district.getEntryDate().getTime()));
        } else {
            this.setEntryDate(new Date(Calendar.getInstance().getTime().getTime()));
        }
        this.setUpdById(district.getUpdById());
        if (district.getUpdDate() != null) {
            this.setUpdDate(new Date(district.getUpdDate().getTime()));
        }
    }
}
