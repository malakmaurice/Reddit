package com.example.reddit.service;

import com.example.reddit.Entity.Post;
import com.example.reddit.Entity.Vote;
import com.example.reddit.Entity.VoteType;
import com.example.reddit.Reposotory.PostRepository;
import com.example.reddit.Reposotory.VoteRepository;
import com.example.reddit.dto.VoteDto;
import com.example.reddit.exception.springRadditException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostService postService;
    private final AuthService authService;
    private final PostRepository postRepository;
    public void vote(VoteDto voteDto) {
        Post post = postService.getPostById(voteDto.getPostId());
       Optional<Vote> vote = voteRepository.findTopByPostAndUser(post, authService.getCurrentUser());
        if (vote.isPresent()) {
            if(vote.get().getVoteType()!=voteDto.getVoteType()){
                if(voteDto.getVoteType()== VoteType.DOWNVOTE)
                    post.setVoteCount(post.getVoteCount()-2);
                else
                    post.setVoteCount(post.getVoteCount()+2);
                vote.get().setVoteType(voteDto.getVoteType());
                voteRepository.save(vote.get());
                postRepository.save(post);
            }else
            {
                throw new springRadditException("You have already "
                        + voteDto.getVoteType() + "'d for this post");
            }
        } else {
            if(voteDto.getVoteType()==VoteType.DOWNVOTE)
                post.setVoteCount(post.getVoteCount()-1);
            else
                post.setVoteCount(post.getVoteCount()+1);
            postRepository.save(post);
            voteRepository.save(mapToVote(voteDto));
        }
      //  voteRepository.save(mapToVote(voteDto));
    }

    private Vote mapToVote(VoteDto voteDto) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(postRepository.findById(voteDto.getPostId()).get())
                .user(authService.getCurrentUser())
                .build();
    }
}
