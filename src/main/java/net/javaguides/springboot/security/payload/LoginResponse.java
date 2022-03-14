package net.javaguides.springboot.security.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.springboot.common.base.ServiceResponse;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse extends ServiceResponse implements Serializable {

    private String userId;
    private String token;
    private Long expiredIn;

    public LoginResponse(String message) {
        super(false, message);
    }

    public LoginResponse(String userId, String token, Long expiredIn) {
        super();
        this.userId = userId;
        this.token = token;
        this.expiredIn = expiredIn;
    }
}
