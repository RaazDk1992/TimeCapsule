package com.raazdk.TimeCapsule.repository;

import com.raazdk.TimeCapsule.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository  extends JpaRepository<Post,Long> {
}
