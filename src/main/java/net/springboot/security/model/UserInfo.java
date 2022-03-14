package net.springboot.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class UserInfo implements Serializable {

    @EmbeddedId
    private UserInfoId userInfoId;

    @Column(name = "password")
    private String password;

    @Column(name="user_type")
    private String userType;

    @Column(name="user_status")
    private String userStatus;

    @Column(name="entry_date")
    private Date entryDate;

    @Column(name="upd_date")
    private Date updDate;
}
