package com.cooksys.assessment1.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	List<UserResponseDto> entitiesToResponseDTOs(List<User> users);
	
	@Mapping(target = "username", source = "credentials.username") //insert the value from credentials.username into the "username" field of response DTO
	UserResponseDto entityResponseDto(User user);

}
