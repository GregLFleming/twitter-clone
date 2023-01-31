package com.cooksys.assessment1.mappers;

import com.cooksys.assessment1.dtos.UserResponseDTO;
import com.cooksys.assessment1.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
        List<UserResponseDTO> entitiesToResponseDTOs(List<User> users);
        UserResponseDTO entityResponseDto(User user);


}
