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
        Subreddit subreddit = mapToSubreddit(subredditDto);
        subreddit = subredditRepository.save(subreddit);
        subredditDto.setId(subreddit.getSubredditId());
        return subredditDto;
    }

    public List<SubredditDto> getAllSubreddits() {
        return subredditRepository
                .findAll().stream().map(this::mapToSubredditDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubredditDtoById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new springRadditException("subreddit id not found"));
        return mapToSubredditDto(subreddit);
    }

    public Subreddit getSubredditById(Long id) {
        return subredditRepository.findById(id)
                .orElseThrow(() -> new springRadditException("subreddit id not found"));

    }
    public Subreddit getSubredditByName(String subredditName) {
        return this.subredditRepository.findByName(subredditName)
                .orElseThrow(() -> new springRadditException("subreddit name " + " Not found"));
    }

    private SubredditDto mapToSubredditDto(Subreddit subreddit) {
        return SubredditDto.builder().id(subreddit.getSubredditId())
                .name(subreddit.getName()).
                descrption(subreddit.getDescrption())
                .postCount(subreddit.getPosts().size()).build();
    }

    public Subreddit mapToSubreddit(SubredditDto subredditDto) {
        return Subreddit.builder()
                .name(subredditDto.getName())
                .descrption(subredditDto.getDescrption())
                .user(authService.getCurrentUser())
                .createdDate(Instant.now())
                .build();
    }
}
