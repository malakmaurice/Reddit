package com.example.reddit.Reposotory;


import com.example.reddit.Entity.Comment;
import com.example.reddit.Entity.Post;
import com.example.reddit.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findAllByPost(Post post);
    Optional<List<Comment>> findAllByUser(User user);
}
