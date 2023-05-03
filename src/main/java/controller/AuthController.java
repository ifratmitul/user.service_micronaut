package controller;

import auth.AuthenticationProviderUserPassword;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.rules.SecurityRule;
import model.AuthResponse;
import model.LoginModel;

import javax.validation.Valid;
import java.security.Principal;
import io.micronaut.security.annotation.Secured;
@Controller("/api/auth")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AuthController {
    private final AuthenticationProviderUserPassword _auth;
    private final JwtGenerator jwtGenerator;

    public AuthController(AuthenticationProviderUserPassword auth) {
        _auth = auth;
    }

    @Post("/login")
    public HttpResponse <AuthResponse> Login(@Body @Valid LoginModel data) {
        try {
            // Attempt authentication with the provided username and password
            Maybe<Authentication> authenticationResult = authenticationProvider.authenticate(
                    new UsernamePasswordCredentials(loginRequest.getEmail(), loginRequest.getPassword()));

            // If authentication is successful, generate a JWT token and return it
            return authenticationResult
                    .switchIfEmpty(Maybe.error(new AuthenticationFailed("Invalid credentials")))
                    .map(authentication -> {
                        UserDetails userDetails = (UserDetails) authentication;
                        Map<String, Object> claims = new HashMap<>();
                        claims.put("sub", userDetails.getUsername());
                        String jwtToken = jwtGenerator.generateToken(claims);
                        return HttpResponse.ok(new AuthResponse("success", jwtToken)).contentType(MediaType.APPLICATION_JSON);
                    })
                    .blockingGet();
        } catch (AuthenticationException e) {
            return HttpResponse.unauthorized(new AuthResponse("error", "Invalid email or password")).contentType(MediaType.APPLICATION_JSON);
        }

    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    public String index(Principal principal) {
        return principal.getName();
    }
}
