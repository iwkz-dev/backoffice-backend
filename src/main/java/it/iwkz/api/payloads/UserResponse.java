package it.iwkz.api.payloads;

import lombok.Data;

@Data
public class UserResponse {
    private String fullName;
    private String username;
    private String roles;
}
