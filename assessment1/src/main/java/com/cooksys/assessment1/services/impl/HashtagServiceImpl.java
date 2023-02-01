package com.cooksys.assessment1.services.impl;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.mappers.HashTagMapper;
import com.cooksys.assessment1.repositories.HashtagRepository;
import com.cooksys.assessment1.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;
    private final HashTagMapper hashtagMapper;
}