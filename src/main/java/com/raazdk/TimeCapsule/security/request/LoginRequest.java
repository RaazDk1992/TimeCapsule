package com.raazdk.TimeCapsule.security.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class LoginRequest {
    private String username;
    private String password;

}