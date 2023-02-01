package com.cooksys.assessment1.services.impl;

import java.util.List;

import com.cooksys.assessment1.dtos.UserRequestDto;
import org.springframework.stereotype.Service;

import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.mappers.UserMapper;
import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

	@Override
	public List<UserResponseDto> getUsers() {
		List<User> queryResult = userRepository.findAllByDeletedFalse();
		if(queryResult.isEmpty()) {
			throw new NotFoundException("There are no users in the database.");
		}
		
		return userMapper.entitiesToResponseDTOs(userRepository.findAllByDeletedFalse());
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		User user = userMapper.RequestDtoToEntity(userRequestDto);
		userRepository.saveAndFlush(user);
		return userMapper.entityToResponseDto(user);
	}
}
