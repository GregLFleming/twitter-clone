package com.cooksys.assessment1.services.impl;

import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.mappers.TweetMapper;
import com.cooksys.assessment1.mappers.UserMapper;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private  final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserMapper userMapper;

    @Override
    public List<TweetResponseDto> getTweets(){
        List<Tweet> queryResult = tweetRepository.findAllByDeletedFalse();
        if(queryResult.isEmpty()){
            throw new NotFoundException("There are no tweets in the database.");
        }
        Collections.sort(queryResult, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet o1, Tweet o2) {
                return o1.getPosted().compareTo(o2.getPosted());
            }
        });
        return tweetMapper.entitiesToResponseDTOs(tweetRepository.findAllByDeletedFalse());
    }

    @Override
    public TweetResponseDto getTweetById(Long id){
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if(tweet.isEmpty()){
            throw new NotFoundException("There is no tweet with id " + id);
        }
        return tweetMapper.entityToDto(tweet.get());
    }
//    Retrieves the active users who have liked the tweet with the given id.
//    If that tweet is deleted or otherwise doesn't exist, an error should be sent in lieu of a response.
    @Override
    public List<UserResponseDto> getTweetLikesById(Long id){
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if(tweet.isEmpty() || !tweet.get().isDeleted()){
            throw new NotFoundException("There is no tweet with id " + id);
        }
        List<User> users = tweet.get().getLikedBy();
        return userMapper.entitiesToResponseDTOs(users);
    }
}
