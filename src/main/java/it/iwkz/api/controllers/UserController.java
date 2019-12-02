package it.iwkz.api.controllers;

import it.iwkz.api.models.User;
import it.iwkz.api.payloads.auth.UserResponse;
import it.iwkz.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public UserResponse getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        User user =  userRepository.findByUsername(userDetails.getUsername()).orElse(null);

        UserResponse userResponse = new UserResponse();
        userResponse.setFullName(user.getFullName());
        userResponse.setUsername(user.getUsername());
        userResponse.setRoles(user.getRoles().toString());

        return userResponse;
    }
}
