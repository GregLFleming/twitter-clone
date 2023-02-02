package com.cooksys.assessment1.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getUsers(){
    	return userService.getUsers();
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto);
    }
    
    @PostMapping("/@{username}/follow")
    public void followUser(@RequestBody CredentialsDto credentialsRequestDto, @PathVariable String username) {
    	userService.followUser(credentialsRequestDto, username);
    }
    
    @PostMapping("/@{username}/unfollow")
    public void unfollowUser(@RequestBody CredentialsDto credentialsRequestDto, @PathVariable String username) {
    	userService.unfollowUser(credentialsRequestDto, username);
    }
    
    @PatchMapping("/@{username}")
    public UserResponseDto updateUsername(@RequestBody UserRequestDto userRequestDto, @PathVariable String username) {
    	return userService.updateUsername(userRequestDto, username);
    }
    
    @GetMapping("/@{username}")
    public UserResponseDto getUser(@PathVariable String username) {
    	return userService.getUser(username);
    	}
    
    @GetMapping("/@{username}/mentions")
    public List<TweetResponseDto> getUserMentions(@PathVariable String username) {
    	return userService.getUserMentions(username);
    	}
    
    @GetMapping("/@{username}/tweets")
    public List<TweetResponseDto> getUserTweets(@PathVariable String username) {
    	return userService.getUserTweets(username);
    	}
    
    @GetMapping("/@{username}/feed")
    public List<TweetResponseDto> getUserFeed(@PathVariable String username) {
    	return userService.getUserFeed(username);
    	}
    
    @DeleteMapping("/@{username}")
    public UserResponseDto deleteUser(@RequestBody CredentialsDto credentials, @PathVariable String username) {
    	return userService.deleteUser(credentials, username);
    	}
}