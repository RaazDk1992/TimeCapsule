package com.raazdk.TimeCapsule.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class PostReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long reactionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("post-reaction")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference("user-reactions")
    private  TUser user;
}
