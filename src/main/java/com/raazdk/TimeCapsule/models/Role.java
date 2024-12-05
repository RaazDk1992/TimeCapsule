package com.raazdk.TimeCapsule.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @ToString.Exclude
    private AppRoles roleName;

    @OneToMany(mappedBy = "role",fetch = FetchType.LAZY,cascade = {CascadeType.MERGE})
    @JsonManagedReference("roles-user")
    private Set<TUser> users;


    public Role(AppRoles roleName) {
        this.roleName = roleName;
    }
}
