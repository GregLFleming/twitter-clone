package com.cooksys.assessment1.repositories;

import com.cooksys.assessment1.entities.Tweet;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

  List<Tweet> findAllByDeletedFalse();

	Optional<Tweet> findByIdAndDeletedFalse(Long id);

}
