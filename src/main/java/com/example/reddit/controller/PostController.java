package com.example.reddit.controller;


import com.example.reddit.dto.ExceptionResponse;
import com.example.reddit.dto.PostRequestDto;
import com.example.reddit.dto.PostResponseDto;
import com.example.reddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> savePost(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.savePost(postRequestDto));
    }

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
    }
}

