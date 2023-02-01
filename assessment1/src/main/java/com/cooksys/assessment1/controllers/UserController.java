package com.cooksys.assessment1.controllers;

import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getUsers(){
    	return userService.getUsers();
    }
    
}
