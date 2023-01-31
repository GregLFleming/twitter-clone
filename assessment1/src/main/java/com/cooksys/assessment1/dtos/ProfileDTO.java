package com.cooksys.assessment1.dtos;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Embeddable
public class ProfileDTO {
    private String firstName;

    private String lastName;

    @NonNull
    private String email;

    private String phone;
}
