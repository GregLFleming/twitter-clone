package com.cooksys.assessment1.mappers;

import org.mapstruct.Mapper;

import com.cooksys.assessment1.dtos.ProfileDto;
import com.cooksys.assessment1.entities.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile requestDtoEntity(ProfileDto profileDTO);
}
