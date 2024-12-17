package com.raazdk.TimeCapsule.security;

import com.raazdk.TimeCapsule.models.AppRoles;
import com.raazdk.TimeCapsule.models.Role;
import com.raazdk.TimeCapsule.models.TUser;
import com.raazdk.TimeCapsule.repository.RolesRepository;
import com.raazdk.TimeCapsule.repository.UserRepository;
import com.raazdk.TimeCapsule.security.jwt.AuthEntryPoint;
import com.raazdk.TimeCapsule.security.jwt.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.time.LocalDate;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity()
@EnableMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class SecurityConfig{

    @Autowired
    AuthEntryPoint authEntryPoint;
    @Bean
    AuthFilter tokenFilter(){
        return new AuthFilter();
    }

    @Bean
    SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf-> csrf.disable());
        http.authorizeHttpRequests((requests)->requests
                .requestMatchers("/api/getcsrf").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/user/**").permitAll());
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint));
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(withDefaults());
        return http.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();

    }


    @Bean
    public CommandLineRunner initData(RolesRepository roleRepository,
                                      UserRepository userRepository
    ) {
        return args -> {
            Role userRole = roleRepository.findByRoleName(AppRoles.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(AppRoles.ROLE_USER)));

            Role adminRole = roleRepository.findByRoleName(AppRoles.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(AppRoles.ROLE_ADMIN)));

            if (!userRepository.existsByusername("user1")) {
                TUser user1 = new TUser("user1",passwordEncoder().encode("password"), "user1@mail.com"
                        );
                user1.setAccountNonLocked(true);
                user1.setAccountNonExpired(true);
                user1.setCredentialsNonExpired(true);
                user1.setEnabled(true);
                user1.setRole(userRole);
                userRepository.save(user1);
            }

            if (!userRepository.existsByusername("admin")) {
                TUser admin = new TUser("admin",  passwordEncoder().encode("password"),"admin@mail.com"
                       );
                admin.setAccountNonLocked(true);
                admin.setAccountNonExpired(true);
                admin.setCredentialsNonExpired(true);
                admin.setEnabled(true);
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }
}
