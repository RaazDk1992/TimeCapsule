package com.raazdk.TimeCapsule.controller;

import com.raazdk.TimeCapsule.models.AppResponse;
import com.raazdk.TimeCapsule.models.Post;
import com.raazdk.TimeCapsule.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createPost( @AuthenticationPrincipal UserDetails details,
                                        @RequestParam String post,
                                        @RequestPart(value = "media",required = false) List<MultipartFile>mediaFiles

                                     ){



        try {
            postService.createPost(details,post,mediaFiles);

            //return new AppResponse(HttpStatus.OK,"Created SuccessfullY",null);
            return  ResponseEntity.ok("Post created..");
        }catch (Exception ex){

            return   ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed with :"+ex.toString());

        }

    }

    @GetMapping("/public")
    public List<Post> loadAll(){

        return postService.loadAll();
    }
    
}
