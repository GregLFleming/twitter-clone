package com.cooksys.assessment1.mappers;

import com.cooksys.assessment1.dtos.ProfileDTO;
import com.cooksys.assessment1.entities.Profile;

public interface ProfileMapper {
    Profile requestDtoEntity(ProfileDTO profileDTO);
}
