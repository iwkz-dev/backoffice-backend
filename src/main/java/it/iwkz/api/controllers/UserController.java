package it.iwkz.api.controllers;

import it.iwkz.api.exceptions.BadRequestException;
import it.iwkz.api.exceptions.ResourceNotFoundException;
import it.iwkz.api.models.Role;
import it.iwkz.api.models.RoleName;
import it.iwkz.api.models.User;
import it.iwkz.api.payloads.EntityResponse;
import it.iwkz.api.payloads.auth.ChangePassword;
import it.iwkz.api.payloads.auth.SingUpRequest;
import it.iwkz.api.payloads.auth.UserResponse;
import it.iwkz.api.repositories.RoleRepository;
import it.iwkz.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody SingUpRequest singUpRequest) {
        if (userRepository.existsByUsername(singUpRequest.getUsername())) {
            throw new BadRequestException("username is already exist");
        }

        User user = new User();
        user.setFullName(singUpRequest.getFullName());
        user.setUsername(singUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(singUpRequest.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);
    }

    @GetMapping
    public EntityResponse<UserResponse> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        User user =  userRepository.findByUsername(userDetails.getUsername()).orElse(null);

        UserResponse userResponse = new UserResponse();
        userResponse.setFullName(user.getFullName());
        userResponse.setUsername(user.getUsername());
        userResponse.setRoles(user.getRoles());

        return new EntityResponse<>(userResponse);
    }

    @PutMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changeUserPassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePassword changePasswordRequest
    ) {
        User user =  userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userName", userDetails.getUsername()));

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        userRepository.save(user);
    }
}
