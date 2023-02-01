package com.cooksys.assessment1.services;

import com.cooksys.assessment1.dtos.TweetResponseDto;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getTweets();

    TweetResponseDto getTweetById(Long id);

}
