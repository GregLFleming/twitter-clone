package com.cooksys.assessment1.dtos;

import java.util.List;

import com.cooksys.assessment1.entities.Tweet;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data
public class ContextDto {
	
	@NonNull
	private Tweet target; 
	
	@NonNull
	private List<Tweet> before;
	
	@NonNull
	private List<Tweet> after;

}
