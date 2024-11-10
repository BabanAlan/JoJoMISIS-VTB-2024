package com.example.controller;

import com.example.service.AuthenticationService;
import com.example.service.MfaRequest;
import com.example.service.MfaService;
import com.example.util.RiskLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MfaService mfaService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        RiskLevel riskLevel = authenticationService.authenticate(username, password);
        if (riskLevel == RiskLevel.LOW) {
            return "Успешная аутентификация без дополнительных факторов";
        } else if (riskLevel == RiskLevel.MEDIUM || riskLevel == RiskLevel.HIGH) {
            return "Требуется дополнительная аутентификация";
        } else {
            return "Неверные учетные данные";
        }
    }

    @PostMapping("/verify-mfa")
    public String verifyMfa(@RequestParam String username, @RequestBody MfaRequest mfaRequest) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            boolean isVerified = mfaService.verifyAdditionalFactors(user, mfaRequest);
            if (isVerified) {
                return "Успешная аутентификация с дополнительными факторами";
            } else {
                return "Неудачная дополнительная аутентификация";
            }
        } else {
            return "Пользователь не найден";
        }
    }
}
