package net.springboot.security.dao;

import net.springboot.common.base.ServiceResponse;
import net.springboot.security.payload.RegisterUserRequest;
import net.springboot.security.payload.UserInfoRequest;
import net.springboot.security.payload.UserInfoResponse;

public interface SecurityDao {
    ServiceResponse register(RegisterUserRequest request);
    UserInfoResponse GetUserInfo(UserInfoRequest request);
}
