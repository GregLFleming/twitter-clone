package com.cooksys.assessment1.services.impl;

import com.cooksys.assessment1.repositories.HashtagRepository;
import com.cooksys.assessment1.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;
//    private final HashtagMapper hashtagMapper;
}
