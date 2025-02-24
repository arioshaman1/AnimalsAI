package com.example.autharization.Controller;

import com.example.autharization.Service.AuthService;
import com.example.autharization.entities.AuthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthEntity> register(@RequestBody AuthEntity authEntity) {
        return ResponseEntity.ok(authService.registerUser(authEntity));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(authService.authenticateUser(username, password));
    }
}
