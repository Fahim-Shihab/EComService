package net.javaguides.springboot.security.service;

import net.javaguides.springboot.common.base.ServiceResponse;
import net.javaguides.springboot.common.util.Utils;
import net.javaguides.springboot.lookup.repository.BaseRepository;
import net.javaguides.springboot.security.config.JwtUtils;
import net.javaguides.springboot.security.model.LoggedInUser;
import net.javaguides.springboot.security.model.UserInfo;
import net.javaguides.springboot.security.model.UserInfoId;
import net.javaguides.springboot.security.payload.LoginRequest;
import net.javaguides.springboot.security.payload.LoginResponse;
import net.javaguides.springboot.security.payload.RegisterUserRequest;
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
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityService {

    @Value("${bezkoder.app.jwtExpirationMs}")
    private Long expiryTime;

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final BaseRepository repository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);

    public SecurityService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, BaseRepository repository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        if(request == null) {
            return new LoginResponse("Login request is required");
        }
        if(!Utils.isOk(request.getUserId())) {
            return new LoginResponse("User Id is required");
        }
        if(!Utils.isOk(request.getPassword())) {
            return new LoginResponse("Password is required");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (request.getUserId(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        LoggedInUser userDetails = (LoggedInUser) authentication.getPrincipal();
        return new LoginResponse(userDetails.getUserId(),
                jwt,
                expiryTime);
    }

    public ServiceResponse register(RegisterUserRequest request) {
        //LoggedInUser user = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Utils.isOk(request.getUserId())) {
            return new ServiceResponse("User id is required");
        }
        if(!Utils.isOk(request.getPassword())) {
            return new ServiceResponse("Password is required");
        }
        if(!Utils.isOk(request.getUserType())) {
            return new ServiceResponse("User type is required");
        }
        if(!Utils.isOk(request.getUserStatus())) {
            return new ServiceResponse("User status is required");
        }

        try {

            String sql = "SELECT * FROM user_info WHERE user_id=:userId ";
            Map<String, Object> params = new HashMap<>();
            params.clear();
            params.put("userId", request.getUserId().trim().toUpperCase());
            UserInfo userInfoEO = repository.findSingleResultByNativeQuery(sql, UserInfo.class, params);
            boolean isUpdate = false;
            if(userInfoEO != null) {
                isUpdate = true;
            } else {
                userInfoEO = new UserInfo();
                UserInfoId userInfoId = new UserInfoId();
                userInfoId.setUserId(request.getUserId().trim().toUpperCase());
                userInfoId.setUserEmail(request.getUserEmail());
                userInfoEO.setUserInfoId(userInfoId);
            }

            Timestamp timestamp = Utils.getCurrentTimeStamp();
            if(!isUpdate) {
                userInfoEO.setEntryDate(timestamp);
                userInfoEO.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            if(isUpdate && request.isResetPassword()) {
                userInfoEO.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            userInfoEO.setUserStatus(request.getUserStatus());
            userInfoEO.setUserType(request.getUserType());
            if(!isUpdate) {
                repository.persist(userInfoEO);
            } else {
                repository.merge(userInfoEO);
            }
        } catch (Throwable t) {
            LOGGER.error("USER CREATION ERROR:", t);
            return new ServiceResponse("Internal server error.Please contact with admin");
        }
        return new ServiceResponse(true, "User has been registered successfully");
    }

}
