package com.raazdk.TimeCapsule.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
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


    private boolean accountNonLocked =true;
    private boolean accountNonExpired =true;
    private boolean credentialsNonExpired =true;
    private boolean enabled = true;

    @ManyToOne
    @JsonBackReference("roles-user")
    @JoinColumn(name = "role_id", referencedColumnName = "roleId")
    private Role role;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference("user-posts")
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference("user-replies")
    private List<PostReply> replies;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("user-reactions")
    private List<PostReaction> reactions;

    public TUser(String username,String password, String email){
        this.username = username;
        this.password = password;
        this.email =email;
    }




    @Override
    public boolean equals(Object o){
        if(this ==o) return  true;
        if(!(o instanceof  TUser)) return  false;
        return userId!=null && userId.equals(((TUser)o).getUserId());
    }

    @Override
    public int hashCode(){ return getUserId().hashCode();}




}
