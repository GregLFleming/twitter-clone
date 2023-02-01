package com.cooksys.assessment1.controllers;

import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getTweets(){

        return tweetService.getTweets();
    }
    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable(name="id") Long id){
        return tweetService.getTweetById(id);
    }

}
