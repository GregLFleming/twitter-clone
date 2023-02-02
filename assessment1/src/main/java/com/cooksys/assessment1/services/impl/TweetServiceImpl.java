package com.cooksys.assessment1.services.impl;

import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.mappers.CredentialsMapper;
import com.cooksys.assessment1.mappers.TweetMapper;
import com.cooksys.assessment1.mappers.UserMapper;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private  final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final CredentialsMapper credentialsMapper;
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        Tweet tweetToAdd = tweetMapper.requestDtoToEntity(tweetRequestDto);
        Credentials credentials = credentialsMapper.requestDtoEntity(tweetRequestDto.getCredentials());
        Optional<User> author = userRepository.findByCredentials(credentials);

        if(author.isEmpty())
            throw new BadRequestException("User with username: " + credentials.getUsername() + " does not exist");

        tweetToAdd.setAuthor(author.get());


        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweetToAdd));
    }
}
