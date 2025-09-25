package com.example.demo.service.auth;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.security.JwtUtil;
import com.example.demo.dto.model.auth.ChangePasswordRequest;
import com.example.demo.dto.model.auth.ForgotPasswordRequest;
import com.example.demo.dto.model.auth.ForgotPasswordResponse;
import com.example.demo.dto.model.auth.LoginRequest;
import com.example.demo.dto.model.auth.LoginResponse;
import com.example.demo.dto.model.auth.RegisterRequest;
import com.example.demo.repository.auth.LoginRepository;
import com.example.demo.repository.auth.RegisterRepository;
import com.example.demo.repository.auth.ChangePasswordRepository;
import com.example.demo.repository.auth.ForgotPasswordRepository;
import com.example.demo.util.AuthUtil;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final LoginRepository loginRepository;
    private final RegisterRepository registerRepository;
    private final ChangePasswordRepository changePasswordRepository;
    private final AuthUtil authUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ForgotPasswordRepository forgotPasswordRepository;

    public AuthService(LoginRepository loginRepository,
            RegisterRepository registerRepository,
            ChangePasswordRepository changePasswordRepository,
            ForgotPasswordRepository forgotPasswordRepository,
            AuthUtil authUtil,
            JwtUtil jwtUtil) {
        this.loginRepository = loginRepository;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.registerRepository = registerRepository;
        this.changePasswordRepository = changePasswordRepository;
        this.authUtil = authUtil;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        return loginRepository.findPasswordByEmail(loginRequest.getUsername())
                .map(storedPassword -> {
                    boolean valid = storedPassword.startsWith("$2a$")
                            ? passwordEncoder.matches(loginRequest.getPassword(), storedPassword)
                            : storedPassword.equals(loginRequest.getPassword());

                    if (valid) {
                        Long userId = loginRepository.findUserIdByEmail(loginRequest.getUsername())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                        String token = jwtUtil.generateToken(userId, 1000 * 60 * 60); // 1 hora
                        return new LoginResponse(true, "Login correcto", token);
                    }
                    return new LoginResponse(false, "Credenciales inválidas", null);
                })
                .orElse(new LoginResponse(false, "Credenciales inválidas", null));
    }

    public boolean registrarUsuario(RegisterRequest request) {
        if (authUtil.usernameExists(request.getUsername()) ||
                authUtil.emailExists(request.getEmail())) {
            return false;
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        int rows = registerRepository.registerUser(request);
        return rows > 0;
    }

    public boolean updatePassword(ChangePasswordRequest request) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        String nuevoPasswordHash = passwordEncoder.encode(request.getNewPassword());
        int filas = changePasswordRepository.updatePassword(userId, nuevoPasswordHash);

        return filas > 0;
    }

    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            return new ForgotPasswordResponse(false, "Debes proporcionar un email válido.", null);
        }

        Optional<Long> userId = forgotPasswordRepository.findUserIdByEmail(request.getEmail());
        if (userId.isEmpty()) {
            return new ForgotPasswordResponse(false, "No se encontró ningún usuario con el email proporcionado.",
                    null);
        }

        Long id = userId.get();
        String resetToken = jwtUtil.generateToken(id, 1000 * 60 * 15);

        return new ForgotPasswordResponse(true, "Token de recuperación generado.", resetToken);
    }
}
