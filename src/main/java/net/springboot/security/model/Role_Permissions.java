package net.springboot.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.springboot.common.enums.UserRole;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role_permissions")
public class Role_Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role_id")
    private UserRole role_id;

    @Column(name = "permission_id")
    private String permission_id;

    @Column(name = "status", length = 1)
    private String status;
}
