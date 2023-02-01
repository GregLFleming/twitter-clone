package com.cooksys.assessment1.repositories;

import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    //    TODO: Add derived queries if necessary
    List<Tweet> findAllByDeletedFalse();
    TweetResponseDto getTweetById(Long id);
}
