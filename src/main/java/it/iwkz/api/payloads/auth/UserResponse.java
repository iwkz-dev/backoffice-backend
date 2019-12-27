package it.iwkz.api.payloads.auth;

import it.iwkz.api.models.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {
    private String fullName;
    private String username;
    private Set<Role> roles;
}
