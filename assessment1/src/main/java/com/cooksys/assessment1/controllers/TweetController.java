package com.cooksys.assessment1.controllers;

import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.services.TweetService;
import com.cooksys.assessment1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweetById(@PathVariable(name="id") Long id,@RequestBody Credentials credentials){
        return tweetService.deleteTweetById(id,credentials);
    }
    @PostMapping("{id}/repost")
    public TweetResponseDto repostTweetById(@PathVariable(name="id") Long id,@RequestBody Credentials credentials){
        return tweetService.repostTweetById(id, credentials);
    }

}