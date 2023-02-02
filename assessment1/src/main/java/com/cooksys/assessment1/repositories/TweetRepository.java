package com.cooksys.assessment1.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;

import jakarta.persistence.OrderBy;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

  List<Tweet> findAllByDeletedFalse();

	Optional<Tweet> findByIdAndDeletedFalse(Long id);

	@OrderBy("posted ASC")
	List<Tweet> findAllByAuthorAndDeletedFalse(User user);

}
