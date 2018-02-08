package com.library.firebaselibrary.models.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDao implements Serializable {
    private String userId;
    private String firstName;
    private String lastName;
    private String auth0Id;
    private String emailAddress;
    private String refreshToken;
}
