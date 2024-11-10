package com.example.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    private String username;
    private String password;
    private String role;

    // Дополнительные поля для MFA
    private String pgpPublicKey;
    private String trustedDeviceId;
    private String yandexAlisaId;
    private Double trustedLatitude;
    private Double trustedLongitude;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities;
}
