package it.iwkz.api.payloads;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
public class JwtAuthenticationResponse {
    private String token;

    @Setter(AccessLevel.NONE)
    private String tokenType = "Bearer";
}
