package com.library.firebaselibrary.models.auth;

import com.library.firebaselibrary.models.auth.AuthProperties.AppMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth0User {

    private String auth0Id;
    private AppMetadata appMetadata;
}
