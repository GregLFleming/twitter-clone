package com.cooksys.assessment1.services.impl;

import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.mappers.CredentialsMapper;
import com.cooksys.assessment1.mappers.TweetMapper;
import com.cooksys.assessment1.mappers.UserMapper;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.TweetService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private  final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserRepository userRepository;
    private final CredentialsMapper credentialsMapper;
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

    @Override
    public void likeTweetById(Long id, Credentials credentials){
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if(tweet.isEmpty() || !tweet.get().isDeleted()){
            throw new NotFoundException("Process finished with exit code 0e is no tweet with id " + id);
        }
        Optional<User> user = userRepository.findByCredentials(credentials);
       if(user.isEmpty()){
           throw new NotFoundException("There is no user with  " + credentials.getUsername());
       }
       List<User> users = tweet.get().getLikedBy();
       users.add(user.get());
       tweet.get().setLikedBy(users);
        tweetRepository.saveAndFlush(tweet.get());
        List<Tweet> tweets = user.get().getLikedTweets();
        tweets.add(tweet.get());
        user.get().setLikedTweets(tweets);
        userRepository.saveAndFlush(user.get());

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