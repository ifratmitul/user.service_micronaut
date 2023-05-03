package model;

import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationResponse;

import java.util.Optional;

public class AuthResponse extends AuthenticationResponse {

    private final String token;

    public AuthResponse(String username, String token) {
        super(username);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
