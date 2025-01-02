package com.raazdk.TimeCapsule.controller;

import com.raazdk.TimeCapsule.models.Post;
import com.raazdk.TimeCapsule.service.PostService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/createpost")
    public ResponseEntity<?>createPost( @AuthenticationPrincipal UserDetails details,
                                        @RequestParam String post,
                                        @RequestPart(value = "media",required = false) List<MultipartFile>mediaFiles

                                     ){



        try {
            postService.createPost(details,post,mediaFiles);
            return new ResponseEntity<>("Post created", HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("failed with :"+ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/public")
    public List<Post> loadAll(){

        return postService.loadAll();
    }
    
}
