package net.springboot.security.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.springboot.common.base.ServiceResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse extends ServiceResponse implements Serializable {

    public UserInfoResponse(){
        userList = new ArrayList<>();
    }

    public UserInfoResponse(String message) {
        super(false, message);
    }

    List<SingleUserInfo> userList;
    int total;
}
