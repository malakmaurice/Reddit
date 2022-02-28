package com.example.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {
    private Long postId;
    private String postName;
    private String url;
    private String description;
    private String subredditName;
    private String userName;
    private Integer commentCount;
    private Integer voteCount;
}
