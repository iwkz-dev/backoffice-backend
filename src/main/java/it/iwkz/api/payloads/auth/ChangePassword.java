package it.iwkz.api.payloads.auth;

import lombok.Data;

@Data
public class ChangePassword {
    private String newPassword;
}
