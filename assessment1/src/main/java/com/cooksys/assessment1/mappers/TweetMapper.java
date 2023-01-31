package com.cooksys.assessment1.mappers;

import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.entities.Tweet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TweetMapper {

    TweetResponseDto entityToDto(Tweet entity);

    Tweet requestDtoToEntity(TweetRequestDto tweetRequestDto);

}
