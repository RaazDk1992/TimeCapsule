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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${media.url}")
    private  String MediaPath;;


    public Post createPost(UserDetails details, String post, List<MultipartFile> mediaFiles) {

        TUser user = userRepository.findByusername(details.getUsername()).orElseThrow(()->new UsernameNotFoundException("Invalid username"));
        Post newpost = new Post();
        newpost.setPostBody(post);
        newpost.setUser(user);
        List<PostMedia> mediaList = new ArrayList<>();
        if(mediaFiles !=null){
            //details.getUsername()+"/media/"+ Instant.now()+file.getOriginalFilename()
            Path uploadPath = Paths.get(uploadDir, user.getUsername(), "media");
            System.out.println("Path"+MediaPath);
            if(!Files.exists(uploadPath)){
                try {
                    Files.createDirectories(uploadPath);

                } catch (Exception e) {

                    throw new RuntimeException(e);
                }
            }

            for (MultipartFile file:mediaFiles){
                String suffix =details.getUsername()+"/media/"+newpost.getUnique()+file.getOriginalFilename();
                String filePathx = uploadDir+"/"+suffix;
                String resourcePath = MediaPath+"/"+suffix;

                try {
                    File targetFile = new File(filePathx);

                    file.transferTo(targetFile);
                } catch (IOException e) {
                    System.out.println("details = " + e.toString());
                    throw new RuntimeException(e);
                }
                PostMedia media = new PostMedia();
                media.setPost(newpost);
                media.setMediaPath(resourcePath);
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
