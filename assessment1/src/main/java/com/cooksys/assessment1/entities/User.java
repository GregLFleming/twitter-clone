package com.cooksys.assessment1.entities;

import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	
	@Embedded
	private Profile profile;
	
}
