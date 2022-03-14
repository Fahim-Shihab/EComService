package net.springboot.security.payload;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest implements Serializable {

    private String userId;
    private String password;
}
