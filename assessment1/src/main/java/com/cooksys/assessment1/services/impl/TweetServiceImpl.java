package com.cooksys.assessment1.services.impl;

import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.mappers.CredentialsMapper;
import com.cooksys.assessment1.mappers.TweetMapper;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.TweetService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private  final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserRepository userRepository;
    private final CredentialsMapper credentialsMapper;
	@Override
	public TweetResponseDto replyTo(TweetRequestDto tweetRequestDto, Long id) {
		
		//Check if tweet exists
		Optional<Tweet> queryResult = tweetRepository.findByIdAndDeletedFalse(id);
		if(queryResult.isEmpty()){
			throw new NotFoundException("A Tweet with id: " + id + " could not be found");
		}
		Tweet tweetRepliedTo = queryResult.get();
		
		//validate user credentials
		Credentials credentials = credentialsMapper.requestDtoEntity(tweetRequestDto.getCredentials());
		Optional<User> userQueryResult = userRepository.findByCredentialsAndDeletedFalse(credentials);
		if(userQueryResult.isEmpty()) {
			throw new NotFoundException("A user with username: " + credentials.getUsername() + " could not be found");
		}
//		User user = userQueryResult.get();
		
		Tweet reply = tweetMapper.requestDtoToEntity(tweetRequestDto);
		reply.setInReplyTo(tweetRepliedTo);
		System.out.println(reply);
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(reply));
	}

}
