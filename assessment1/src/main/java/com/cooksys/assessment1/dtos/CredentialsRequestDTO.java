package com.cooksys.assessment1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CredentialsRequestDTO {
    private String username;
    private String password;
}
