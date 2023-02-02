package com.cooksys.assessment1.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/{id}/reply")
    public TweetResponseDto replyTo(@RequestBody TweetRequestDto tweetRequestDto, @PathVariable Long id) {
    	return tweetService.replyTo(tweetRequestDto, id);
    }
    
}
