package net.springboot.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class GenericModel implements Serializable {

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
