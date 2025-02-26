package com.example.autharization.Service;
import com.example.autharization.DTO.AuthEntityDTO;
import com.example.autharization.JWT.JwtUtil;
import com.example.autharization.Repositories.AuthRepository;
import com.example.autharization.entities.AuthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthEntity registerUser(AuthEntityDTO authDTO) {
        logger.info("Registering user: {}", authDTO.getUsername());
        if (authRepository.findByUsername(authDTO.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        AuthEntity authEntity = new AuthEntity();
        authEntity.setUsername(authDTO.getUsername());
        authEntity.setPassword(passwordEncoder.encode(authDTO.getPassword()));
        authEntity.setEmail(authDTO.getEmail());

        logger.info("Saving user to database: {}", authEntity.getUsername());
        return authRepository.save(authEntity);
    }

    public String authenticateUser(String username, String password) {
        logger.info("Authenticating user: {}", username);
        AuthEntity user = authRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Проверяем пароль
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Генерируем токен
        return jwtUtil.generateToken(username);
    }
}