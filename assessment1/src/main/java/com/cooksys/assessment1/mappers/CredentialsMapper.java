package com.cooksys.assessment1.mappers;

import com.cooksys.assessment1.dtos.CredentialsRequestDTO;
import com.cooksys.assessment1.entities.Credentials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
    Credentials requestDtoEntity(CredentialsRequestDTO credentialsRequestDTO);
}
