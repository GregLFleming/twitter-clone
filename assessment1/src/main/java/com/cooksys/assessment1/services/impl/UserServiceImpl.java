package com.cooksys.assessment1.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.mappers.CredentialsMapper;
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

	@Override
	public List<UserResponseDto> getUsers() {
		return userMapper.entitiesToResponseDTOs(userRepository.findAllByDeletedFalse());
	}

	@Override
	public void followUser(CredentialsDto credentialsRequestDto, String username) {
		
		//check if user to follow exists
		Optional<User> queryResult = userRepository.findByCredentialsUsername(username);
		if(queryResult.isEmpty()) {
			throw new NotFoundException("A user with username " + username + " could not be found");
		}
		User userToFollow = queryResult.get();
		
		//check if credentials submitted exist
		System.out.println(credentialsRequestDto);
		queryResult = userRepository.findByCredentials(credentialsMapper.requestDtoEntity(credentialsRequestDto));
		if(queryResult.isEmpty()) {
			throw new NotFoundException("Your credentials cannot be found in the database");
		}
		User user = queryResult.get();
		
		//check if user is already following userToFollow
		if(userToFollow.getFollowers().contains(user)) {
			throw new BadRequestException("You are already following the user with username: " + username + "!" );
		}
		
		//Make sure user and userToFollow are different
		if(userToFollow.getId() == user.getId()) {
			throw new BadRequestException("You cannot follow yourself!" );
		}
		
		//update followers and following fields, insert into database.
		List<User> userIsFollowing = user.getFollowing();
		List<User> userToFollowFollowers = userToFollow.getFollowers();	
		userIsFollowing.add(userToFollow);
		userToFollowFollowers.add(user);
		user.setFollowing(userIsFollowing);
		userToFollow.setFollowers(userToFollowFollowers);
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(userToFollow);
		
		return;
	}

	@Override
	public void unfollowUser(CredentialsDto credentialsRequestDto, String username) {
		
		//check if user to unfollow exists
		Optional<User> queryResult = userRepository.findByCredentialsUsername(username);
		if(queryResult.isEmpty()) {
			throw new NotFoundException("A user with username " + username + " could not be found");
		}
		User userToUnfollow = queryResult.get();
		
		//check if credentials submitted exist
		queryResult = userRepository.findByCredentials(credentialsMapper.requestDtoEntity(credentialsRequestDto));
		if(queryResult.isEmpty()) {
			throw new NotFoundException("Your credentials cannot be found in the database");
		}
		User user = queryResult.get();
		
		//check if user is not yet following userToFollow
		if(!userToUnfollow.getFollowers().contains(user)) {
			throw new BadRequestException("You are not yet following the user with username: " + username + "!" );
		}
		
		//Make sure user and userToFollow are different
		if(userToUnfollow.getId() == user.getId()) {
			throw new BadRequestException("You cannot unfollow yourself!" );
		}
		
		//update followers and following fields, insert into database.
		List<User> userIsFollowing = user.getFollowing();
		List<User> userToUnfollowFollowers = userToUnfollow.getFollowers();	
		userIsFollowing.remove(userToUnfollow);
		userToUnfollowFollowers.remove(user);
		user.setFollowing(userIsFollowing);
		userToUnfollow.setFollowers(userToUnfollowFollowers);
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(userToUnfollow);
		
		return;
	}

	@Override
	public UserResponseDto updateUsername(UserRequestDto userRequestDto, String username) {
		//check if credentials submitted exist
		Optional<User> queryResult = userRepository.findByCredentials(userMapper.userRequestDtoToEntity(userRequestDto).getCredentials());
		if(queryResult.isEmpty()) {
			throw new NotFoundException("Your credentials cannot be found in the database");
		}
		User user = queryResult.get();
		return userMapper.entityResponseDto(user);
	}
}
