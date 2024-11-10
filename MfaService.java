package com.example.service;

import com.example.model.User;
import org.springframework.stereotype.Service;

@Service
public class MfaService {

    public void requestAdditionalFactors(User user) {
        // Отправка уведомления пользователю о необходимости пройти MFA
        // Это может быть отправка сообщения на e-mail, пуш-уведомление и т.д.
        System.out.println("Требуется дополнительная аутентификация для пользователя: " + user.getUsername());
    }

    public boolean verifyAdditionalFactors(User user, MfaRequest mfaRequest) {
        // Проверка всех дополнительных факторов
        boolean isGpsVerified = verifyLocation(user, mfaRequest.getLatitude(), mfaRequest.getLongitude());
        boolean isBluetoothVerified = verifyBluetoothDevice(user, mfaRequest.getDeviceId());
        boolean isPgpVerified = verifyPGPSignature(user, mfaRequest.getSignedMessage());
        boolean isYandexAlisaVerified = verifyYandexAlisa(user, mfaRequest.getVoiceCode());

        return isGpsVerified && isBluetoothVerified && isPgpVerified && isYandexAlisaVerified;
    }

    public boolean verifyLocation(User user, Double latitude, Double longitude) {
        // Проверка, что переданные координаты находятся в допустимом диапазоне
        double threshold = 0.01; // Допустимая погрешность
        if (user.getTrustedLatitude() != null && user.getTrustedLongitude() != null) {
            double latDiff = Math.abs(user.getTrustedLatitude() - latitude);
            double lonDiff = Math.abs(user.getTrustedLongitude() - longitude);
            return latDiff <= threshold && lonDiff <= threshold;
        }
        return false;
    }

    public boolean verifyBluetoothDevice(User user, String deviceId) {
        // Проверка соответствия ID устройства
        return deviceId != null && deviceId.equals(user.getTrustedDeviceId());
    }

    public boolean verifyPGPSignature(User user, String signedMessage) {
        // Проверка PGP-подписи сообщения
        // Реализация проверки подписи с использованием Bouncy Castle
        // Для простоты пример возвращает true
        return true;
    }

    public boolean verifyYandexAlisa(User user, String voiceCode) {
        // Интеграция с API Яндекс.Алисы для проверки голосового кода
        // Для простоты пример возвращает true
        return true;
    }
}
