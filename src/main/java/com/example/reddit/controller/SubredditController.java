package com.example.reddit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {

    @GetMapping
    public ResponseEntity<String> getAllSubreddits(){
        return ResponseEntity.status(HttpStatus.OK).body("all Subreddit");
    }
}
