package com.cooksys.assessment1.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Hashtag {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String label;
	
	@Column(nullable = false)
	private java.sql.Timestamp firstUsed;
	
	@Column(nullable = false)
	private java.sql.Timestamp lastUsed;
	
	@Column(nullable = false)
	@ManyToMany(mappedBy = "hashtags")
	private List<Tweet> tweets;
}

