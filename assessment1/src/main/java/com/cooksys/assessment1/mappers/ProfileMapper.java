package com.cooksys.assessment1.mappers;

import com.cooksys.assessment1.dtos.ProfileDto;
import com.cooksys.assessment1.entities.Profile;

public interface ProfileMapper {
    Profile requestDtoEntity(ProfileDto profileDTO);
}
