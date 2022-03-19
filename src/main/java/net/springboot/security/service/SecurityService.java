package net.springboot.security.service;

import net.springboot.common.base.Defs;
import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.Utils;
import net.springboot.lookup.repository.BaseRepository;
import net.springboot.security.config.JwtUtils;
import net.springboot.security.model.LoggedInUser;
import net.springboot.security.model.UserInfo;
import net.springboot.security.model.UserInfoId;
import net.springboot.security.payload.*;
import net.springboot.security.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SecurityService {

    @Value("${bezkoder.app.jwtExpirationMs}")
    private Long expiryTime;

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final SecurityRepository securityRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);

    public SecurityService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, SecurityRepository securityRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.securityRepository = securityRepository;
    }

    public LoginResponse login(LoginRequest request) {
        if (request == null) {
            return new LoginResponse("Login request is required");
        }
        if (!Utils.isOk(request.getUserId())) {
            return new LoginResponse("User Id is required");
        }
        if (!Utils.isOk(request.getPassword())) {
            return new LoginResponse("Password is required");
        }
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken
                (request.getUserId(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        LoggedInUser userDetails = (LoggedInUser) authentication.getPrincipal();
        return new LoginResponse(userDetails.getUserId(), jwt, expiryTime);
    }

    public ServiceResponse register(RegisterUserRequest request) {

        if (!Utils.isOk(request.getUserId())) {
            return new ServiceResponse("User id is required");
        }
        if (!Utils.isOk(request.getPassword())) {
            return new ServiceResponse("Password is required");
        }
        if (!Utils.isOk(request.getUserRole())) {
            return new ServiceResponse("User Role is required");
        }
        if (!Utils.isOk(request.getUserStatus())) {
            return new ServiceResponse("User status is required");
        }

        return securityRepository.register(request);
    }

    public UserInfoResponse GetUserInfo(UserInfoRequest request) {

        return securityRepository.GetUserInfo(request);
    }

}
