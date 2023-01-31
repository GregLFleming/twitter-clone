package com.cooksys.assessment1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Where(clause = "deleted=false")
@Table(name="user_table")
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Embedded
	private Credentials credentials;
	
	@Column(nullable = false)
	private java.sql.Timestamp joined;
	
	private boolean deleted = false;

	@ManyToMany
	private List<User> followers;

	@ManyToMany
	private List<User> following;

	@ManyToMany
	private List<Tweet> likedTweets;

	@ManyToMany
	private List<Tweet> mentions;

	
	@Embedded
	private Profile profile;
	
}
