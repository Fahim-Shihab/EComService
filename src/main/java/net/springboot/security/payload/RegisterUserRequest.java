package net.springboot.security.payload;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.springboot.common.enums.Status;
import net.springboot.common.enums.UserRole;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUserRequest implements Serializable {

    String userId;
    String userEmail;
    String password;
    UserRole userRole;
    Status userStatus;
    String contact;
    String address;

    boolean resetPassword;
}
