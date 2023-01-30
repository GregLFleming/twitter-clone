package com.cooksys.assessment1.entities;

import java.util.List;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
	@JoinColumn(name = "in_reply_to")
	private Tweet inReplyTo;
	
	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replies;
	
	@ManyToOne
	@JoinColumn(name = "repost_of")
	private Tweet repostOf;
	
	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> reposts;
	
	@ManyToMany
	@JoinTable(name = "tweet_hashtags", 
			  joinColumns = @JoinColumn(name = "tweet_id"), 
			  inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
	private List<Hashtag> hashtags;
	
//	@ManyToMany
//	private List<User> tweetsLiked;
	
	

}
