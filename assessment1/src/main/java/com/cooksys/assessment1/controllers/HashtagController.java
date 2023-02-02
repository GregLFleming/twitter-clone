package com.cooksys.assessment1.controllers;

import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class HashtagController {
    private final HashtagService hashtagService;

    @GetMapping
    public List<HashtagDto> getHashtags(){
        return hashtagService.getHashtags();
    }
}