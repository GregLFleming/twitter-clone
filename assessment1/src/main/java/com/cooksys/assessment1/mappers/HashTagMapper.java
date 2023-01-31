package com.cooksys.assessment1.mappers;

import com.cooksys.assessment1.entities.Hashtag;
import com.cooksys.assessment1.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashTagMapper {
    List<Tweet> requestDtoEntity(Hashtag hashtag);


}
