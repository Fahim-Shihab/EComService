package net.springboot.security.payload;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.springboot.common.base.Page;
import net.springboot.common.enums.Status;
import net.springboot.common.enums.UserRole;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoRequest extends Page {

    String userId;
    String userEmail;
    String fullName;
    UserRole userRole;
    Status userStatus;
}
