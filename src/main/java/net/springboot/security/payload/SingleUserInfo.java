package net.springboot.security.payload;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.enums.Status;
import net.springboot.common.enums.UserRole;

import java.io.Serializable;

@Getter
@Setter
public class SingleUserInfo  implements Serializable {
    long Id;
    String userId;
    String userEmail;
    String fullName;
    String userRole;
    String userStatus;
    String contact;
    String address;
}
