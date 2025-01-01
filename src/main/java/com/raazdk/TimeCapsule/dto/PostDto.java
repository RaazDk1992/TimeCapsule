package com.raazdk.TimeCapsule.dto;

import java.util.List;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostDto {
    String post;
    List<MultipartFile> images;
}
