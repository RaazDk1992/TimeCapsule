package com.raazdk.TimeCapsule.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor@NoArgsConstructor
@Data
public class PostReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long replyId;
    String replyBody;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("post-reply")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-replies")
    private  TUser user;

}
