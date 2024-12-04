package com.raazdk.TimeCapsule.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @CreationTimestamp
    LocalDateTime dateJoined;


    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled = true;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference("roles-user")
    @JoinColumn(name = "role_id", referencedColumnName = "roleId")
    private Role role;

    @Override
    public boolean equals(Object o){
        if(this ==o) return  true;
        if(!(o instanceof  TUser)) return  false;
        return userId!=null && userId.equals(((TUser)o).getUserId());
    }

    @Override
    public int hashCode(){ return getUserId().hashCode();}

}
