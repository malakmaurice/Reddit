package com.example.reddit.Reposotory;

import com.example.reddit.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
        Optional<VerificationToken> findByToken(String token);

}
