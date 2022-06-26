package com.emrhnsyts.leaveamark.controller;

import com.emrhnsyts.leaveamark.entity.AppUser;
import com.emrhnsyts.leaveamark.request.AppUserRequest;
import com.emrhnsyts.leaveamark.response.AppUserCreateResponse;
import com.emrhnsyts.leaveamark.response.AppUserResponse;
import com.emrhnsyts.leaveamark.security.jwt.JwtTokenManager;
import com.emrhnsyts.leaveamark.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping("/register")
    public AppUserCreateResponse register(@RequestBody @Valid AppUserRequest appUserRequest) {
        return appUserService.addUser(appUserRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid AppUserRequest appUserRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    appUserRequest.getUsername(), appUserRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final AppUser appUser = appUserService.loadUserByUsername(appUserRequest.getUsername());
        final String jwt = jwtTokenManager.generateToken(appUser);
        return jwt;

    }

    @GetMapping("/users/{userId}")
    public AppUserResponse getUser(@PathVariable("userId") Long userId) {
        return appUserService.getUser(userId);
    }

    @GetMapping("/users")
    public List<AppUserResponse> getUsers() {
        return appUserService.getUsers();
    }
}
