package com.cooksys.assessment1.entities;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Credentials {
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
}
