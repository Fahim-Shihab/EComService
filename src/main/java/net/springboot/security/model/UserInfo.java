package net.springboot.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {

//    @EmbeddedId
//    private UserInfoId userInfoId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="user_id", unique = true)
    private String userId;

    @Column(name="user_email")
    private String userEmail;

    @Column(name = "password")
    private String password;

    @Column(name="full_name")
    private String fullName;

    @Column(name="role")
    private String role;

    @Column(name="user_status", length = 1)
    private String userStatus;

    @Column(name = "contact")
    private String contact;

    @Column(name = "address")
    private String address;

    @Column(name="entry_date")
    private Date entryDate;

    @Column(name="upd_date")
    private Date updDate;
}
