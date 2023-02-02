package com.cooksys.assessment1.services.impl;

import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.entities.Hashtag;
import org.springframework.stereotype.Service;

import com.cooksys.assessment1.mappers.HashTagMapper;
import com.cooksys.assessment1.repositories.HashtagRepository;
import com.cooksys.assessment1.services.HashtagService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;
    private final HashTagMapper hashtagMapper;

    @Override
    public List<HashtagDto> getHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();

        return hashtagMapper.entitiesToDtos(hashtags);
    }

//    @Override
//    public boolean checkHashTagExist(String label){
//        System.out.println("Label service " + label);
//        List<Hashtag> hashtags = hashtagRepository.findAll();
//        boolean labelExist=false;
//        for(Hashtag hashtag : hashtags){
//            if(hashtag.getLabel() == label) labelExist = true;
//        }
//
//        return labelExist;
//
//    }
}
