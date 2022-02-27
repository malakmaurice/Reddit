package com.example.reddit.service;

import com.example.reddit.Entity.Subreddit;
import com.example.reddit.Reposotory.SubredditRepository;
import com.example.reddit.dto.SubredditDto;
import com.example.reddit.exception.springRadditException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final AuthService authService;

    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit=mapToSubreddit(subredditDto);
        subreddit= subredditRepository.save(subreddit);
        subredditDto.setId(subreddit.getSubredditId());
        return subredditDto;
    }

    public Subreddit mapToSubreddit (SubredditDto subredditDto){
        return Subreddit.builder()
                .name("/r/"+subredditDto.getName())
                .descrption(subredditDto.getDescrption())
                .user(authService.getCurrentUser())
                .createdDate(Instant.now())
                .build();
    }

    public List<SubredditDto> getAllSubreddits() {
        return subredditRepository
                .findAll().stream().map(this::mapToSubredditDto)
                .collect(Collectors.toList());
    }

    private SubredditDto mapToSubredditDto(Subreddit subreddit) {
       return SubredditDto.builder().id(subreddit.getSubredditId())
                .name(subreddit.getName()).
                descrption(subreddit.getDescrption())
                .postCount(subreddit.getPosts().size()).build();
    }
    public SubredditDto getSubreddit(Long id){
      Subreddit subreddit=  subredditRepository.findById(id)
              .orElseThrow(() -> new springRadditException("subreddit id not found"));
      return mapToSubredditDto(subreddit);
    }
}
