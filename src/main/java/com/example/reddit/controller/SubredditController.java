package com.example.reddit.controller;

import com.example.reddit.dto.SubredditDto;
import com.example.reddit.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subreddit")
public class SubredditController {
    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> saveSubreddit(@RequestBody SubredditDto subredditDto){
              return  ResponseEntity.status(HttpStatus.ACCEPTED)
                      .body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return ResponseEntity.status(HttpStatus.OK).
                body(subredditService.getAllSubreddits());
    }
    @GetMapping("/{subreditId}")
    public ResponseEntity<Object> getSubreddit(@PathVariable Long subreditId){
       try {
           SubredditDto subredditDto =subredditService.getSubreddit(subreditId);
           return ResponseEntity.status(HttpStatus.OK)
                   .body(subredditDto);
       }catch (Exception e ){
           return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("subreddit id not found");
       }

    }
}
