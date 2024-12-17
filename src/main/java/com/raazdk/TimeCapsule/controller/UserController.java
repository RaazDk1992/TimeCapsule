package com.raazdk.TimeCapsule.controller;

import com.raazdk.TimeCapsule.models.TUser;
import com.raazdk.TimeCapsule.security.response.UserInfoResponse;
import com.raazdk.TimeCapsule.service.TUserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    TUserService userService;
    @GetMapping("/hi")
    public String hi(){
        return "hi";
    }

    @GetMapping("/getuser")
    public ResponseEntity<?> getuser(@AuthenticationPrincipal UserDetails details){

        TUser user = userService.getUserByUserName(details.getUsername())
                .orElseThrow(()->new UsernameNotFoundException("Invalid user Details"));
        List<String> roles = details.getAuthorities().stream()
                .map(item->item.getAuthority()).collect(Collectors.toList());
        UserInfoResponse response = new UserInfoResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                roles

        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
