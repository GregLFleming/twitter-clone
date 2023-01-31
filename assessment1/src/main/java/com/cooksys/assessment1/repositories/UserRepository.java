package com.cooksys.assessment1.repositories;

import com.cooksys.assessment1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    TODO: Add derived queries if necessary

}
