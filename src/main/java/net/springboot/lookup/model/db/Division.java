package net.springboot.lookup.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.springboot.common.model.GenericModel;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "division")
public class Division extends GenericModel implements Serializable {

    @Id
    @Column(name="id")
    private int divisionId;

    @Column(name = "division_name")
    private String divisionName;

    public Division(Division division) {
        this.divisionId = division.getDivisionId();
        this.divisionName = division.getDivisionName();
        this.setEntryById(division.getEntryById());
        if (division.getEntryDate() != null) {
            this.setEntryDate(new Date(division.getEntryDate().getTime()));
        } else {
            this.setEntryDate(new Date(Calendar.getInstance().getTime().getTime()));
        }
        this.setUpdById(division.getUpdById());
        if (division.getUpdDate() != null) {
            this.setUpdDate(new Date(division.getUpdDate().getTime()));
        }
    }
}
