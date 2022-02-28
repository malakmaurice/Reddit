package com.example.reddit.service;

import com.example.reddit.Entity.Comment;
import com.example.reddit.Entity.Post;
import com.example.reddit.Entity.User;
import com.example.reddit.Reposotory.CommentRepository;
import com.example.reddit.dto.CommentDto;
import com.example.reddit.dto.NotificationEmail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final PostService postService;
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final MailService mailService;

    public Object saveComment(CommentDto commentDto) {
        Comment comment=this.commentRepository.save(mapToComment(commentDto));
        commentDto.setCreatedDate(comment.getCreatedDate());
        commentDto.setId(comment.getCommentId());
        NotificationEmail notificationEmail =
                new NotificationEmail("User Commented on Your Post",comment.getPost().getUser().getEmail(),
                        commentDto.getUserName()+" Commented on Your Post");
        mailService.sendMail(notificationEmail);
        return commentDto;
    }

    private Comment mapToComment(CommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .createdDate(Instant.now())
                .user(userDetailsServiceImp.getUserByUserName(commentDto.getUserName()))
                .post(postService.getPostById(commentDto.getPostId()))
                .build();
    }

    public List<Object> getCommentsByPostId(Long postId) {
        Post post=this.postService.getPostById(postId);
       return this.commentRepository.findAllByPost(post).get()
               .stream().map(this::mapTpCommentDto).collect(Collectors.toList());
    }

    private CommentDto  mapTpCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getCommentId())
                .userName(comment.getUser().getUserName())
                .createdDate(comment.getCreatedDate())
                .postId(comment.getPost().getPostId())
                .text(comment.getText())
                .build();
    }

    public List<Object>  getCommentsByUserName(String userName) {
        User user=this.userDetailsServiceImp.getUserByUserName(userName);
        return this.commentRepository.findAllByUser(user).get()
                .stream().map(this::mapTpCommentDto).collect(Collectors.toList());
    }
}
