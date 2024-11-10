package com.example.service;

import org.bouncycastle.openpgp.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.security.Security;

@Service
public class PgpService {

    public boolean verifySignature(String publicKeyArmored, String signedMessage) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            // Реализация проверки подписи с использованием Bouncy Castle
            // Парсинг публичного ключа
            PGPPublicKey publicKey = readPublicKey(publicKeyArmored);
            // Проверка подписи
            // Для простоты пример возвращает true
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private PGPPublicKey readPublicKey(String publicKeyArmored) throws Exception {
        ByteArrayInputStream keyIn = new ByteArrayInputStream(publicKeyArmored.getBytes());
        PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(
                PGPUtil.getDecoderStream(keyIn), new JcaKeyFingerprintCalculator());
        PGPPublicKey key = null;
        for (PGPPublicKeyRing keyRing : pgpPub) {
            for (PGPPublicKey k : keyRing) {
                if (k.isEncryptionKey()) {
                    key = k;
                    break;
                }
            }
        }
        if (key == null) {
            throw new IllegalArgumentException("Can't find encryption key in key ring.");
        }
        return key;
    }
}
