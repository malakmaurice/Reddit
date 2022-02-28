package com.example.reddit.Reposotory;

import com.example.reddit.Entity.Post;
import com.example.reddit.Entity.Subreddit;
import com.example.reddit.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findBySubreddit(Subreddit subreddit);
    Optional<List<Post>> findByUser (User user);
}
