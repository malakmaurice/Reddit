package com.example.reddit.controller;

import com.example.reddit.dto.ExceptionResponse;
import com.example.reddit.dto.VoteDto;
import com.example.reddit.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vote")
@AllArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity vote(@RequestBody VoteDto voteDto){
        try {
            voteService.vote(voteDto);
            return  ResponseEntity.status(HttpStatus.OK).body("you "+voteDto.getVoteType()+" the post");
        }catch (Exception e){
            ExceptionResponse exceptionResponse=
                    new ExceptionResponse(e.toString(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);

        }
    }

}
