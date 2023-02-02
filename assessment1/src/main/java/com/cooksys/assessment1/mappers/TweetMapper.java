package com.cooksys.assessment1.mappers;

import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TweetMapper.class})
public interface TweetMapper {

    TweetResponseDto entityToDto(Tweet entity);

    Tweet requestDtoToEntity(TweetRequestDto tweetRequestDto);
    List<TweetResponseDto> entitiesToResponseDTOs(List<Tweet> tweets);

}
