package com.example.service;

import lombok.Data;

@Data
public class MfaRequest {
    private Double latitude;
    private Double longitude;
    private String deviceId;
    private String signedMessage;
    private String voiceCode;
}
