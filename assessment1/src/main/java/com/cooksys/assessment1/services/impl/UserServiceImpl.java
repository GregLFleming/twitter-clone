package com.cooksys.assessment1.services.impl;

import java.util.List;
import java.util.Optional;

import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.mappers.CredentialsMapper;
import org.springframework.data.domain.Example;
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

	private final CredentialsMapper credentialsMapper;

	private void validateUserRequest(UserRequestDto userRequestDto){

		if(userRequestDto.getProfile().getEmail() == null || userRequestDto.getCredentials().getPassword() == null || userRequestDto.getCredentials().getUsername() == null){
			throw new BadRequestException("Request body missing required fields");
		}

	}

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
		validateUserRequest(userRequestDto);

		User user = userMapper.requestDtoToEntity(userRequestDto);
		Optional<User> check = userRepository.findByCredentials(user.getCredentials());

		if(!check.isEmpty() && check.get().isDeleted()) {
			user = check.get();
			user.setDeleted(false);
		}

		return userMapper.entityToResponseDto(userRepository.saveAndFlush(user));
	}
}
