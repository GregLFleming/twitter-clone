package com.cooksys.assessment1.services;

import com.cooksys.assessment1.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

	List<UserResponseDto> getUsers();

}
