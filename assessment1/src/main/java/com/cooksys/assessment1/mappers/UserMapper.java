package com.cooksys.assessment1.mappers;

import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
        List<UserResponseDto> entitiesToResponseDTOs(List<User> users);
        UserResponseDto entityResponseDto(User user);


}
