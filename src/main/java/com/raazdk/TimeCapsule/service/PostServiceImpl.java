package com.raazdk.TimeCapsule.service;

import com.raazdk.TimeCapsule.models.Post;
import com.raazdk.TimeCapsule.models.PostMedia;
import com.raazdk.TimeCapsule.models.TUser;
import com.raazdk.TimeCapsule.repository.PostRepository;
import com.raazdk.TimeCapsule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Value("${file.upload-dir}")
    private String uploadDir;


    public Post createPost(UserDetails details, String post, List<MultipartFile> mediaFiles) {

        TUser user = userRepository.findByusername(details.getUsername()).orElseThrow(()->new UsernameNotFoundException("Invalid username"));
        Post newpost = new Post();
        newpost.setPostBody(post);
        newpost.setUser(user);
        List<PostMedia> mediaList = new ArrayList<>();
        if(mediaFiles !=null){

            Path uploadPath = Paths.get(uploadDir, user.getUsername(), "media");
            if(!Files.exists(uploadPath)){
                try {
                    Files.createDirectories(uploadPath);
                } catch (Exception e) {

                    throw new RuntimeException(e);
                }
            }
            for (MultipartFile file:mediaFiles){
                Path filePath = uploadPath.resolve(file.getOriginalFilename());
                System.out.println("Resolved Upload Directory: " + Paths.get(uploadDir).toAbsolutePath());

                try {
                    file.transferTo(filePath.toFile());
                } catch (IOException e) {
                    System.out.println("details = " + e.toString());
                    throw new RuntimeException(e);
                }
                PostMedia media = new PostMedia();
                media.setPost(newpost);
                media.setMediaPath(filePath.toString());
                mediaList.add(media);
            }
        }
        newpost.setMedia(mediaList);
        return postRepository.save(newpost);


    }

    public List<Post> loadAll() {
        return postRepository.findAll();
    }
}
