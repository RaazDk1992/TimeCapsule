package com.raazdk.TimeCapsule.security.service;

import com.raazdk.TimeCapsule.models.TUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor @AllArgsConstructor
@Data
public class TuserDetails  implements UserDetails {

    private static final Long serialVersionUid = 1L;

    public TuserDetails(String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles =  authorities;
    }
    private Long userId;
    private String username;
    private String password;
    private String email;
    private boolean isAccountNonExpired;
    private boolean isAccountNonlocked;
    private boolean isEnabled;

    private Collection<?extends GrantedAuthority> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  roles;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    public static UserDetails build(TUser tusr){
        GrantedAuthority authority = new SimpleGrantedAuthority(tusr.getRole().getRoles().name());
        return  new TuserDetails(
                tusr.getUsername(),
                tusr.getPassword(),
                tusr.getEmail(),
                List.of(authority)
        );
    }

    @Override
    public boolean equals(Object o){
        if(this ==o) return  true;
        if(o==null || getClass() != o.getClass()) return  false;
        TuserDetails details = (TuserDetails) o;
        return Objects.equals(details.userId,((TuserDetails) o).userId);
    }
}
