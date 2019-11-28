package it.iwkz.api.controllers;

import it.iwkz.api.configs.jwt.JwtTokenProvider;
import it.iwkz.api.models.Role;
import it.iwkz.api.models.RoleName;
import it.iwkz.api.models.User;
import it.iwkz.api.payloads.ApiResponse;
import it.iwkz.api.payloads.JwtAuthenticationResponse;
import it.iwkz.api.payloads.LoginRequest;
import it.iwkz.api.payloads.SingUpRequest;
import it.iwkz.api.repositories.RoleRepository;
import it.iwkz.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody SingUpRequest singUpRequest) {
        if (userRepository.existsByUsername(singUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "username already exist"), HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setFullName(singUpRequest.getFullName());
        user.setUsername(singUpRequest.getUsername());
        user.setPassword(singUpRequest.getPassword());

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "user added"));
    }

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);

        return ResponseEntity.ok(response);
    }
}
