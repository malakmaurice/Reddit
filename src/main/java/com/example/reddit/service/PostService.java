package com.example.reddit.service;

import com.example.reddit.Entity.Post;
import com.example.reddit.Entity.Subreddit;
import com.example.reddit.Entity.User;
import com.example.reddit.Reposotory.CommentRepository;
import com.example.reddit.Reposotory.PostRepository;
import com.example.reddit.dto.PostRequestDto;
import com.example.reddit.dto.PostResponseDto;
import com.example.reddit.exception.springRadditException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AuthService authService;
    private final SubredditService subredditService;
    private final UserDetailsServiceImp userDetailsService;
    private final CommentRepository commentRepository;

    public PostResponseDto savePost(PostRequestDto postRequestDto) {
      Post post = this.postRepository.save(mapToPost(postRequestDto));
        return mapToPostResponseDto(post);
    }

    public PostResponseDto mapToPostResponseDto(Post post){
        return PostResponseDto
                .builder()
                .postId(post.getPostId())
                .subredditName(post.getSubreddit().getName())
                .userName(post.getUser().getUserName())
                .url(post.getUrl())
                .postName(post.getPostName())
                .description(post.getDescription())
                .voteCount(post.getVoteCount())
                .commentCount(commentRepository.findAllByPost(post).get().size())
                .build();
    }
    public Post mapToPost(PostRequestDto postRequestDto){
        return Post.builder()
                .postName(postRequestDto.getPostName())
                .description(postRequestDto.getDescription())
                .url(postRequestDto.getUrl())
                .createdDate(Instant.now())
                .subreddit(this.subredditService.getSubredditByName(postRequestDto.getSubredditName()))
                .user(this.authService.getCurrentUser())
                .voteCount(0)
                .build();
    }

    public List<PostResponseDto> getAllPosts() {
        return this.postRepository.findAll()
                .stream().map(this::mapToPostResponseDto)
                .collect(Collectors.toList());
    }

    public PostResponseDto getPostResponseDtoById(Long id) {
        Post post= this.postRepository.findById(id)
                .orElseThrow(()->new springRadditException("post Not Found "+id.toString()));
        return mapToPostResponseDto(post);
    }

    public Post getPostById(Long id) {
        return this.postRepository.findById(id)
                .orElseThrow(()->new springRadditException("post Not Found "+id.toString()));
    }

    public List<Object> getPostsBySubreddit(Long id) {
        Subreddit subreddit= this.subredditService.getSubredditById(id);
        return this.postRepository.findBySubreddit(subreddit).get()
                .stream().map(this::mapToPostResponseDto).collect(Collectors.toList());
    }

    public List<Object> getPostsByUserName(Long id) {
        User user=this.userDetailsService.getUserById(id);
        return this.postRepository.findByUser(user).get()
                .stream().map(this::mapToPostResponseDto).collect(Collectors.toList());
    }
}
