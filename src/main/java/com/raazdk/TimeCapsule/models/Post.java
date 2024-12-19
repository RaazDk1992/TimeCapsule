package com.raazdk.TimeCapsule.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.raazdk.TimeCapsule.models.PostMedia;
import com.raazdk.TimeCapsule.models.PostReaction;
import com.raazdk.TimeCapsule.models.PostReply;
import com.raazdk.TimeCapsule.models.TUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 1000, name = "post")
    private String postBody;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonBackReference("user-posts")
    private TUser user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference("post-media")
    private List<PostMedia> media; // mappedBy indicates this is the inverse side of the relationship

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference("post-reaction")
    private List<PostReaction> reaction;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference("post-reply")
    private List<PostReply> reply;
}
