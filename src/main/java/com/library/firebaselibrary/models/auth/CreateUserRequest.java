package com.library.firebaselibrary.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isAdmin;
}
