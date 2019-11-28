package it.iwkz.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {
    @GetMapping
    public ResponseEntity getFinanceCurrentMonth() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping
    public ResponseEntity test() {
        return ResponseEntity.ok("test add");
    }
}
