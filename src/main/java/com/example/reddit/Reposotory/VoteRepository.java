package com.example.reddit.Reposotory;


import com.example.reddit.Entity.Post;
import com.example.reddit.Entity.User;
import com.example.reddit.Entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUser (Post post, User user);
}
