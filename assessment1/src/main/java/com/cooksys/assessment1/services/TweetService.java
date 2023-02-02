package com.cooksys.assessment1.services;

import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;

public interface TweetService {
    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);
    
    TweetResponseDto replyTo(TweetRequestDto tweetRequestDto, Long id);
}
