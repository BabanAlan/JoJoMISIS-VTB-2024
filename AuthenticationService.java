package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.util.RiskAssessmentUtil;
import com.example.util.RiskLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RiskAssessmentUtil riskAssessmentUtil;

    @Autowired
    private MfaService mfaService;

    public RiskLevel authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Оценка риска
            RiskLevel riskLevel = riskAssessmentUtil.assessRisk(user);
            if (riskLevel == RiskLevel.HIGH || riskLevel == RiskLevel.MEDIUM) {
                // Запрос дополнительных факторов
                mfaService.requestAdditionalFactors(user);
            }
            return riskLevel;
        }
        return RiskLevel.INVALID;
    }
}
