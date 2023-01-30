package com.cooksys.assessment1.entities;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Id;

import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
