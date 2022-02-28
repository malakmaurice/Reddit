package com.example.reddit.controller;

import com.example.reddit.dto.ExceptionResponse;
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
    public ResponseEntity<Object> saveSubreddit(@RequestBody SubredditDto subredditDto){
              try{
                  return  ResponseEntity.status(HttpStatus.ACCEPTED)
                          .body(subredditService.save(subredditDto));
              }catch (Exception e){
                  ExceptionResponse exceptionResponse=
                          new ExceptionResponse(e.toString(), e.getMessage());
                  return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                          .body(exceptionResponse);
              }
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return ResponseEntity.status(HttpStatus.OK).
                body(subredditService.getAllSubreddits());
    }
    @GetMapping("/{subreditId}")
    public ResponseEntity<Object> getSubreddit(@PathVariable Long subreditId){
       try {
           SubredditDto subredditDto =subredditService.getSubredditDtoById(subreditId);
           return ResponseEntity.status(HttpStatus.OK)
                   .body(subredditDto);
       }catch (Exception e ){
           return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("subreddit id not found");
       }

    }
}
