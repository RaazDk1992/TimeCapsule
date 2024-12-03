package com.raazdk.TimeCapsule.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor @AllArgsConstructor
public class TUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    String customUserId;
    String firstName;
    String lastName;
    String email;
    String phone;
    String username;
    String password;
    String avtarLink;

}
