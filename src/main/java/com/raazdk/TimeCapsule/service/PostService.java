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
import java.util.ArrayList;
import java.util.List;

@Service
public interface PostService {

    public Post createPost(UserDetails details, String post, List<MultipartFile> mediaFiles);

    public List<Post> loadAll();
}
