package com.cooksys.assessment1.entities;

import java.util.List;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Where(clause = "deleted=false")
public class Tweet {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
//	@JoinColumn(name = "user_table_id")
	private User author;

	@Column(nullable = false)
	private java.sql.Timestamp posted;
	
	private boolean deleted = false;
	
	private String content;
	
	@ManyToOne
	private List<Tweet> inReplyTo;
	
	@OneToMany
	private Tweet repostOf;
	
	
	@ManyToMany
	private List<User> tweetsLiked;
	


	
	
	
	

}
