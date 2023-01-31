package com.cooksys.assessment1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDTO {
    private CredentialsDTO credentials;
    private ProfileDTO profile;

}
