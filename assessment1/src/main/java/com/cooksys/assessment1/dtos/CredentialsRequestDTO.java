package com.cooksys.assessment1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data
public class CredentialsRequestDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
