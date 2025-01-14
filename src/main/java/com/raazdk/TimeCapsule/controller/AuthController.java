package com.raazdk.TimeCapsule.controller;

import com.raazdk.TimeCapsule.security.jwt.JwtUtils;
import com.raazdk.TimeCapsule.security.request.LoginRequest;
import com.raazdk.TimeCapsule.security.request.RegisterRequest;
import com.raazdk.TimeCapsule.security.response.LoginResponse;
import com.raazdk.TimeCapsule.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    TUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest requestDto){

        Authentication authentication;
        try {

            authentication = manager
                    .authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(),requestDto.getPassword()));

        } catch (Exception e) {
            Map<String,Object> response= new HashMap<>();
            response.put("status","fail");
            response.put("error","Bad Credentials");
            System.out.println(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            //throw new RuntimeException(e);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails details = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateJwtFromUsername(details);
        List<String> roles = details.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());
        LoginResponse successresponse = new LoginResponse(details.getUsername(),roles,jwtToken);


        return new ResponseEntity<>(successresponse,HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        Authentication authentication = null;
        try {

            String result = userService.createUser(request);
            if(result.toLowerCase().contains("created")){
                    authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

            } else {
                throw new Exception(result);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            assert authentication != null;
            UserDetails details = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtUtils.generateJwtFromUsername(details);
            List<String> roles = details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            LoginResponse response = new LoginResponse(details.getUsername(),roles,jwtToken);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {

            Map<String,Object> result = new HashMap<>();
            result.put("status","failed");
            result.put("message",e.getMessage().toString());
            System.out.println("Error :"+e.getMessage());
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
            //throw new RuntimeException(e);
        }

    }

}
