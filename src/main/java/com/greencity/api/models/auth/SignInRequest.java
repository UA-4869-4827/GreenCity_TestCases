package com.greencity.api.models.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    private String email;
    private String password;

    /**
     * Required by User service. Use {@code GREENCITY} for this project.
     */
    private String projectName;
}
