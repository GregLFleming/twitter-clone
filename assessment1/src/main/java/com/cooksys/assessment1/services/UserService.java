package com.cooksys.assessment1.services;

import java.util.List;

import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;

public interface UserService {

	List<UserResponseDto> getUsers();

    UserResponseDto createUser(UserRequestDto userRequestDto);
}
