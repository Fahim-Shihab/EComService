package net.javaguides.springboot.security.payload;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUserRequest implements Serializable {

    String userId;
    String userEmail;
    String password;
    String userType;
    String userStatus;

    boolean resetPassword;
}
