package com.cooksys.assessment1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
	
//	@Column(nullable = false)
//	@ManyToMany
//	private List<Tweet> tweets;
}

