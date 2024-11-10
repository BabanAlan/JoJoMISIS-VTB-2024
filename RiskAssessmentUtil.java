package com.example.util;

import com.example.model.User;
import org.springframework.stereotype.Component;

@Component
public class RiskAssessmentUtil {

    public RiskLevel assessRisk(User user) {
        // Простая оценка риска на основе роли пользователя
        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            return RiskLevel.HIGH;
        } else if (user.getRole().equalsIgnoreCase("USER")) {
            return RiskLevel.MEDIUM;
        } else {
            return RiskLevel.LOW;
        }
    }
}
