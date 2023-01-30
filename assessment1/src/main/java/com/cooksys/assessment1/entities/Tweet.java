package com.cooksys.assessment1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Data
@NoArgsConstructor
@Where(clause = "deleted=false")
public class Tweet {
	
	@Id
	@GeneratedValue
	private Long id;
	
	
//	@ManyToOne
//	@JoinColumn(name = "user_table_id")
//	private User author;

	@Column(nullable = false)
	private java.sql.Timestamp posted;
	
	private boolean deleted = false;
	
	private String content;
	
//	@OneToMany(mappedBy = "inReplyTo")
//	private List<Tweet> replies;
//	
//	@Column(nullable = false)
//	@ManyToOne
//	@JoinColumn(name = "replies")
//	private Tweet inReplyTo;
	
//	private Integer repostOf ????????????;
	
	
	
	

}
