package com.cooksys.assessment1.controllers;

import com.cooksys.assessment1.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;


}
