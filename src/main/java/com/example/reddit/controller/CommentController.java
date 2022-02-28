package com.example.reddit.controller;


import com.example.reddit.dto.CommentDto;
import com.example.reddit.dto.ExceptionResponse;
import com.example.reddit.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Object> savePost(@RequestBody CommentDto commentDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(commentService.saveComment(commentDto));
        } catch (Exception e) {
            ExceptionResponse exceptionResponse =
                    new ExceptionResponse(e.toString(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exceptionResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<Object>> getCommentsByPostId(@RequestParam("postId") Long postId){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(commentService.getCommentsByPostId(postId));
        } catch (Exception e) {
            ExceptionResponse exceptionResponse =
                    new ExceptionResponse(e.toString(), e.getMessage());
            List<Object> res=new ArrayList<>();
            res.add(exceptionResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(res);
        }
    }

    @GetMapping("by-username/{userName}")
    public ResponseEntity<List<Object>> getCommentsByUserName(@PathVariable String userName){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(commentService.getCommentsByUserName(userName));
        } catch (Exception e) {
            ExceptionResponse exceptionResponse =
                    new ExceptionResponse(e.toString(), e.getMessage());
            List<Object> res=new ArrayList<>();
            res.add(exceptionResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(res);
        }
    }
/*
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getAllPosts());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getPostById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(postService.getPostById(id));
        } catch (Exception e) {
            ExceptionResponse exceptionResponse=
                    new ExceptionResponse(e.toString(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exceptionResponse);
        }
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<Object>> getPostBySubreddit(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body( postService.getPostsBySubreddit(id));
        }catch (Exception e){
            List<Object> res=new ArrayList<>();
            ExceptionResponse exceptionResponse=
                    new ExceptionResponse(e.toString(), e.getMessage());
            res.add(exceptionResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }
    @GetMapping("by-username/{id}")
    public ResponseEntity<List<Object>> getPostByUserName(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body( postService.getPostsByUserName(id));
        }catch (Exception e){
            List<Object> res=new ArrayList<>();
            ExceptionResponse exceptionResponse=
                    new ExceptionResponse(e.toString(), e.getMessage());
            res.add(exceptionResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }*/
}

