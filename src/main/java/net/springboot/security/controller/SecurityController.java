package net.javaguides.springboot.security.controller;

import io.swagger.annotations.ApiOperation;
import net.javaguides.springboot.common.base.ServiceResponse;
import net.javaguides.springboot.security.payload.LoginRequest;
import net.javaguides.springboot.security.payload.LoginResponse;
import net.javaguides.springboot.security.payload.RegisterUserRequest;
import net.javaguides.springboot.security.service.SecurityService;
import net.javaguides.springboot.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

}
