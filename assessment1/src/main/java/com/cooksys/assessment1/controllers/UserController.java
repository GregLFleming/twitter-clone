package com.cooksys.assessment1.controllers;

import java.util.List;


import com.cooksys.assessment1.dtos.UserRequestDto;
import org.springframework.web.bind.annotation.*;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.User;
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
}
