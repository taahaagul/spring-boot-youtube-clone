package com.taahaagul.youtubeclone.controller;

import com.taahaagul.youtubeclone.service.UserRegistirationService;
import com.taahaagul.youtubeclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistirationService userRegistirationService;
    private final UserService userService;

    @GetMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        userRegistirationService.registerUser(jwt.getTokenValue());
        return "User Registration successfull";
    }

    @PostMapping("subscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean subscribeUser(@PathVariable String userId) {
         userService.subscribeUser(userId);
         return true;
    }

    @PostMapping("unSubscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean unSubscribeUser(@PathVariable String userId) {
        userService.unSubscribeUser(userId);
        return true;
    }

    @GetMapping("/{userId}/history")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> userHistory(@PathVariable String userId) {
        return userService.userHistory(userId);
    }
}
