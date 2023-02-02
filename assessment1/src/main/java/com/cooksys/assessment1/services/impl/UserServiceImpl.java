package com.cooksys.assessment1.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.entities.Profile;
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

    private void validateUserRequest(UserRequestDto userRequestDto){
    	if(userRequestDto.getCredentials() == null || userRequestDto.getProfile() == null) {
    		throw new BadRequestException("Request body missing either credentials or profile.");
    	}
		if(userRequestDto.getProfile().getEmail() == null || userRequestDto.getCredentials().getPassword() == null || userRequestDto.getCredentials().getUsername() == null){
			throw new BadRequestException("Request body missing required fields");
		}

	}
    
    @Override
	public List<UserResponseDto> getUsers() {
		return userMapper.entitiesToResponseDTOs(userRepository.findAllByDeletedFalse());
	}
    
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		validateUserRequest(userRequestDto);

		User user = userMapper.requestDtoToEntity(userRequestDto);
		Optional<User> check = userRepository.findByCredentials(user.getCredentials());
		if(!check.isEmpty())
			throw new BadRequestException("User with this username already exists");

		if(!check.isEmpty() && check.get().isDeleted()) {
			user = check.get();
			user.setDeleted(false);
		}
		return userMapper.entityToResponseDto(userRepository.saveAndFlush(user));
	}

	@Override
	public UserResponseDto updateUsername(UserRequestDto userRequestDto, String username) {
		
		//check for missing profile
		if(userRequestDto.getProfile() == null) {
			throw new BadRequestException("The request body must contain a profile");
		}
		
		//check if user exists
		Optional<User> queryResult = userRepository.findByCredentials(userMapper.userRequestDtoToEntity(userRequestDto).getCredentials());
		if(queryResult.isEmpty()) {
			throw new NotFoundException("Your credentials cannot be found in the database");
		}
		User user = queryResult.get();
		
		//check if user is deleted
		if(user.isDeleted()) {
			throw new BadRequestException("Username " + user.getCredentials().getUsername() + " has been deleted.");
		}

		//update username
		Credentials newCredentials = user.getCredentials();
		newCredentials.setUsername(username);
		user.setCredentials(newCredentials);
		
//		//update profile
		Profile newProfile = userMapper.requestDtoToEntity(userRequestDto).getProfile();
		
		//validate and set newProfile
		if(newProfile.getEmail() != null) {
			user.setProfile(newProfile);
		}
		
		//save to database
		return userMapper.entityToResponseDto(userRepository.saveAndFlush(user));
	}
	
	@Override
	public void followUser(CredentialsDto credentialsRequestDto, String username) {
		
		//check if user to follow exists
		Optional<User> queryResult = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
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
		Optional<User> queryResult = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
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
}
