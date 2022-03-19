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
        if (request == null) {
            return new LoginResponse("Login request is required");
        }
        if (!Utils.isOk(request.getUserId())) {
            return new LoginResponse("User Id is required");
        }
        if (!Utils.isOk(request.getPassword())) {
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

        try {

            String sql = "SELECT * FROM user_info WHERE user_id=:userId ";
            Map<String, Object> params = new HashMap<>();
            params.clear();
            params.put("userId", request.getUserId().trim().toUpperCase());
            UserInfo userInfoEO = repository.findSingleResultByNativeQuery(sql, UserInfo.class, params);
            boolean isUpdate = false;
            if (userInfoEO != null) {

                if (userInfoEO.getId() == request.getId()) {
                    isUpdate = true;
                } else {
                    return new ServiceResponse(false, "This userId already exists. Please use different userId");
                }

            } else {
                userInfoEO = new UserInfo();
//                UserInfoId userInfoId = new UserInfoId();
//                userInfoId.setUserId(request.getUserId().trim().toUpperCase());
//                userInfoId.setUserEmail(request.getUserEmail());
//                userInfoEO.setUserInfoId(userInfoId);

                userInfoEO.setUserId((request.getUserId().trim().toUpperCase()));
                userInfoEO.setUserEmail(request.getUserEmail());
                userInfoEO.setFullName(request.getFullName());
            }

            Timestamp timestamp = Utils.getCurrentTimeStamp();
            if (!isUpdate) {
                userInfoEO.setEntryDate(timestamp);
                userInfoEO.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            if (isUpdate && request.isResetPassword()) {
                userInfoEO.setUpdDate(timestamp);
                userInfoEO.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            userInfoEO.setUserStatus(request.getUserStatus().getCode());
            userInfoEO.setRole(request.getUserRole().getCode());
            userInfoEO.setContact(request.getContact());
            userInfoEO.setAddress(request.getAddress());

            if (!isUpdate) {
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

    public UserInfoResponse GetUserInfo(UserInfoRequest request) {

        UserInfoResponse userInfoResponse = new UserInfoResponse();

        String sql = "SELECT * FROM user_info WHERE 1=1 ";
        Map<String, Object> params = new HashMap<>();
        if (Utils.isOk(request.getUserId())) {
            sql += " AND user_id=:userId ";
            params.put("userId", request.getUserId().toUpperCase());
        }
        if (Utils.isOk(request.getFullName())) {
            sql += " AND full_name=:fullName ";
            params.put("fullName", request.getFullName());
        }
        if (Utils.isOk(request.getUserEmail())) {
            sql += " AND user_email=:email ";
            params.put("email", request.getUserEmail());
        }
        if (Utils.isOk(request.getUserRole())) {
            sql += " AND role=:userRole ";
            params.put("userRole", request.getUserRole().getCode());
        }
        if (Utils.isOk(request.getUserStatus())) {
            sql += " AND user_status=:status ";
            params.put("status", request.getUserStatus().getCode());
        }
        if (request.getPage() - 1 <= 0) {
            request.setPage(0);
        } else {
            request.setPage(request.getPage() - 1);
        }
        if (request.getSize() == 0) {
            request.setSize(Defs.DEFAULT_LIMIT);
        }

        List<UserInfo> userInfoList = repository.findByNativeQuery
                (sql, UserInfo.class, params, request.getPage() * request.getSize(), request.getSize());
        if (userInfoList != null) {

            List<SingleUserInfo> list = new ArrayList<>();
            userInfoList.forEach(userInfo -> {
                SingleUserInfo obj = new SingleUserInfo();
                obj.setId(userInfo.getId());
                obj.setUserId(userInfo.getUserId());
                obj.setUserEmail(userInfo.getUserEmail());
                obj.setUserRole(userInfo.getRole());
                obj.setUserStatus(userInfo.getUserStatus());
                obj.setContact(userInfo.getContact());
                obj.setAddress(userInfo.getAddress());

                list.add(obj);
            });
            userInfoResponse.setUserList(list);
            userInfoResponse.setTotal(list.size());

            return userInfoResponse;
        }

        return new UserInfoResponse("Failed to fetch user info");
    }

}
