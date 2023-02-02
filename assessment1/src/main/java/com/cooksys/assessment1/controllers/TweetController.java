package com.cooksys.assessment1.controllers;

import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.services.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.services.TweetService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;

    @GetMapping
    public List<TweetResponseDto> getTweets(){

        return tweetService.getTweets();
    }
    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable(name="id") Long id){
        return tweetService.getTweetById(id);
    }
    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getTweetLikesById(@PathVariable(name="id") Long id){
        return tweetService.getTweetLikesById(id);
    }

    @PostMapping("/{id}/like")
    public void likeTweetById(@PathVariable(name="id") Long id, @RequestBody Credentials credentials){
        tweetService.likeTweetById(id, credentials);
    }

    @PostMapping
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto){
        return tweetService.createTweet(tweetRequestDto);
    }
    
    @PostMapping("/{id}/reply")
    public TweetResponseDto replyTo(@RequestBody TweetRequestDto tweetRequestDto, @PathVariable Long id) {
    	return tweetService.replyTo(tweetRequestDto, id);
    }