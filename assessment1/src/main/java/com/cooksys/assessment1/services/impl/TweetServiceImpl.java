package com.cooksys.assessment1.services.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import com.cooksys.assessment1.entities.Hashtag;
import com.cooksys.assessment1.repositories.HashtagRepository;
import org.springframework.stereotype.Service;

import com.cooksys.assessment1.dtos.ContextDto;
import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.mappers.CredentialsMapper;
import com.cooksys.assessment1.mappers.HashTagMapper;
import com.cooksys.assessment1.mappers.TweetMapper;
import com.cooksys.assessment1.mappers.UserMapper;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.TweetService;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private  final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserRepository userRepository;
    private final CredentialsMapper credentialsMapper;
    private final UserMapper userMapper;
    private final HashTagMapper hashtagMapper;

    private final HashtagRepository hashtagRepository;

    private void setupHashtag(Tweet tweetToAdd) {
        List<String> hashtagStrings = new ArrayList<>();
        List<Hashtag> hashtags = new ArrayList<>();

        String contentArray[] = tweetToAdd.getContent().split(" ");
        for (String word : contentArray) {
            if (word.charAt(0) == '#') {
                hashtagStrings.add(word);
            }
        }

        for (String label : hashtagStrings) {
            Hashtag tag = new Hashtag();
            label = label.toLowerCase();
            tag.setLabel(label);
            Optional<Hashtag> check = hashtagRepository.findByLabel(label);

            if (!check.isEmpty())
                tag = check.get();

            tag.setLastUsed(Timestamp.valueOf(LocalDateTime.now()));
            hashtagRepository.saveAndFlush(tag);
            hashtags.add(tag);
        }

        tweetToAdd.setHashtags(hashtags);
    }

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

    private void setupMentions (Tweet tweetToAdd){

        String contentArray[] = tweetToAdd.getContent().split(" ");
        List<String> mentionsStrings = new ArrayList<>();

        List<User> users = new ArrayList<>();

        for (String word : contentArray) {
            if (word.charAt(0) == '@')
                mentionsStrings.add(word.substring(1));
        }

        for (String username : mentionsStrings) {
            Optional<User> mention = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
            if (!mention.isEmpty())
                users.add(mention.get());
        }

        for (User user : users) {
            List<Tweet> mentions = user.getMentions();
            mentions.add(tweetToAdd);
            user.setMentions(mentions);
            userRepository.saveAndFlush(user);
        }

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
    public void likeTweetById(Long id, Credentials credentials) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty() || !tweet.get().isDeleted()) {
            throw new NotFoundException("Process finished with exit code 0e is no tweet with id " + id);
        }
        Optional<User> user = userRepository.findByCredentials(credentials);
        if (user.isEmpty()) {
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
    }
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
    public TweetResponseDto createTweet (TweetRequestDto tweetRequestDto){
        Tweet tweetToAdd = tweetMapper.requestDtoToEntity(tweetRequestDto);
        Credentials credentials = credentialsMapper.requestDtoEntity(tweetRequestDto.getCredentials());
        Optional<User> author = userRepository.findByCredentials(credentials);

        if (author.isEmpty())
            throw new BadRequestException("User with username: " + credentials.getUsername() + " does not exist");

        tweetToAdd.setAuthor(author.get());

        setupHashtag(tweetToAdd);
        tweetRepository.saveAndFlush(tweetToAdd);
        setupMentions(tweetToAdd);


        return tweetMapper.entityToDto(tweetToAdd);
    }
    @Override
    public TweetResponseDto deleteTweetById(Long id, Credentials credentials){
        Optional<Tweet> tweet = tweetRepository.findById(id);
        //check to see if tweet exists.
        if(tweet.isEmpty()){
            throw new NotFoundException("There is no tweet with id " + id);
        }
//        check to see if user creditentials exist
        Optional<User> user = userRepository.findByCredentials(credentials);
        if (user.isEmpty() || tweet.get().getAuthor().getCredentials().getUsername().equals(credentials.getUsername()) ) {
            throw new NotFoundException("There is no user with  " + credentials.getUsername());
        }
        tweet.get().setDeleted(true);
        tweetRepository.saveAndFlush(tweet.get());
        return tweetMapper.entityToDto(tweet.get());
    }

    @Override
    public TweetResponseDto repostTweetById(Long id, Credentials credentials){

        Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(id);

        if(tweet.isEmpty() || tweet.get().isDeleted()){
            throw new NotFoundException("There is no tweet with id " + id);
        }
        Optional<User> user = userRepository.findByCredentials(credentials);

        if(user.isEmpty() || tweet.get().getAuthor().getCredentials().getUsername().equals(credentials.getUsername()))
            throw new BadRequestException("User with username: " + credentials.getUsername() + " does not exist");
        System.out.println("tweet "+ tweet.get().isDeleted());
        System.out.println("user "+ user.isEmpty());
        Tweet tweetToRepost = tweet.get();
        User userReposting = user.get();
        Tweet repost = tweetToRepost;
        repost.setRepostOf(repost);
        repost.setAuthor(userReposting);
        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repost));
    }

    @Override
    public List<UserResponseDto> getMentionsById(Long id){
        Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(id);
        if(tweet.isEmpty() || tweet.get().isDeleted()){
            throw new NotFoundException("There is no tweet with id " + id);
        }
        List<User> users = tweet.get().getMentionedBy();
        users = users.stream().filter(user -> !user.isDeleted()).collect(Collectors.toList());
        return userMapper.entitiesToResponseDTOs(users);
    }


	@Override
	public List<TweetResponseDto> getTweetReposts(Long id) {
		/**Get all reposts of tweet with id = id.
		 * Inputs: id
		 * Output: List<TweetResponseDto>
		 */
		Optional<Tweet> queryResult = tweetRepository.findById(id);
        //check to see if tweet exists.
        if(queryResult.isEmpty()){
            throw new NotFoundException("There is no tweet with id " + id);
        }
        Tweet tweet = queryResult.get();
	
		return tweetMapper.entitiesToResponseDTOs(tweet.getReposts());
	}

	@Override
	public List<TweetResponseDto> getTweetReplies(Long id) {
		/**Get all replies of tweet with id = id.
		 * Inputs: id
		 * Output: List<TweetResponseDto>
		 */
		Optional<Tweet> queryResult = tweetRepository.findById(id);
        //check to see if tweet exists.
        if(queryResult.isEmpty()){
            throw new NotFoundException("There is no tweet with id " + id);
        }
        Tweet tweet = queryResult.get();
	
		return tweetMapper.entitiesToResponseDTOs(tweet.getReplies());
	}

	@Override
	public ContextDto getTweetContext(Long id) {
		/**Get context of tweet with id = id.
		 * Inputs: id
		 * Output: ContextResponseDto
		 */
		Optional<Tweet> queryResult = tweetRepository.findById(id);
        //check to see if tweet exists.
        if(queryResult.isEmpty()){
            throw new NotFoundException("There is no tweet with id " + id);
        }
        
        Tweet tweet = queryResult.get();
        
		return tweetMapper.tweetEntityToContextDto(tweet);
	}

	@Override
	public List<HashtagDto> getTweetTags(Long id) {
		/**Get all Hashtags of tweet with id = id.
		 * Inputs: id
		 * Output: List<hashtagDto>
		 */
		Optional<Tweet> queryResult = tweetRepository.findById(id);
        //check to see if tweet exists.
        if(queryResult.isEmpty()){
            throw new NotFoundException("There is no tweet with id " + id);
        }
        Tweet tweet = queryResult.get();
	
		return hashtagMapper.entitiesToDtos(tweet.getHashtags());
	}


}