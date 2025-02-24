package com.example.autharization.Service;
import com.example.autharization.JWT.JwtUtil;
import com.example.autharization.Repositories.AuthRepository;
import com.example.autharization.entities.AuthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Регистрация пользователя
    public AuthEntity registerUser(AuthEntity authEntity) {
        authEntity.setPassword(passwordEncoder.encode(authEntity.getPassword()));
        return authRepository.save(authEntity);
    }

    // Аутентификация пользователя
    public String authenticateUser(String username, String password) {
        AuthEntity user = authRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(user.getUsername());
        }
        throw new RuntimeException("Invalid credentials");
    }
}