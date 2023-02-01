package com.cooksys.assessment1.mappers;

import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {
	
	List<UserResponseDto> entitiesToResponseDTOs(List<User> users);
	
	@Mapping(target = "username", source = "credentials.username") //insert the value from credentials.username into the "username" field of response DTO
	UserResponseDto entityResponseDto(User user);

}
