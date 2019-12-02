package it.iwkz.api.payloads;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private Object message;
}
