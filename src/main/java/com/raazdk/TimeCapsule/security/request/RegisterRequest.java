package com.raazdk.TimeCapsule.security.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String password_confirm;


}
