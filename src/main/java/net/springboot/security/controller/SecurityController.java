package net.springboot.security.controller;

import net.springboot.common.base.ServiceResponse;
import net.springboot.security.payload.*;
import net.springboot.security.service.SecurityService;
import net.springboot.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    private final UserService userService;
    private final SecurityService securityService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

    public SecurityController(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public @ResponseBody
    ServiceResponse register(@RequestBody RegisterUserRequest request) {
        return securityService.register(request);
    }

    @PostMapping("/signin")
    public @ResponseBody
    LoginResponse login(@RequestBody LoginRequest request) {
        return securityService.login(request);
    }

    @PostMapping("/getuserinfo")
    public @ResponseBody
    UserInfoResponse getUserInfo(@RequestBody UserInfoRequest request) {
        return securityService.GetUserInfo(request);
    }

}
