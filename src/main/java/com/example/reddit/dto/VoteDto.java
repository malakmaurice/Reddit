package com.example.reddit.dto;

import com.example.reddit.Entity.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
private VoteType voteType;
private Long postId;
}
