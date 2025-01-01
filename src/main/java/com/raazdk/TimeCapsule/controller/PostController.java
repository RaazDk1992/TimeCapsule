package com.raazdk.TimeCapsule.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @PostMapping("/createpost")
    public ResponseEntity<?>createPost( @AuthenticationPrincipal UserDetails details,
                                        @RequestParam String post,
                                        @RequestPart(value = "media",required = false) List<MultipartFile>mediaFiles

                                     ){

        return null;
    }
    
}
