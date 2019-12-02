package it.iwkz.api.payloads.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SingUpRequest {
    @NotBlank
    @Size(min = 10, max = 50)
    private String fullName;

    @NotBlank
    @Size(min = 4, max = 30)
    private String username;

    @NotBlank
    @Size(min = 6, max = 20 )
    private String password;
}
