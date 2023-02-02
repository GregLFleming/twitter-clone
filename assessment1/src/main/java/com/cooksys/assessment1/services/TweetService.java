package com.cooksys.assessment1.services;

import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.dtos.TweetRequestDto;

import java.util.List;

public interface TweetService {

    List<TweetResponseDto> getTweets();

    TweetResponseDto getTweetById(Long id);
    List<UserResponseDto> getTweetLikesById(Long id);
    void likeTweetById(Long id, Credentials credentials);

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);
    
    TweetResponseDto replyTo(TweetRequestDto tweetRequestDto, Long id);

    TweetResponseDto deleteTweetById(Long id, Credentials credentials);

    TweetResponseDto repostTweetById(Long id, Credentials credentials);
}