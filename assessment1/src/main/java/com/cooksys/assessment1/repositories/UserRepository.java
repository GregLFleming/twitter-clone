package com.cooksys.assessment1.repositories;

import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllByDeletedFalse();

	Optional<User> findByCredentials( Credentials credentials);
}
