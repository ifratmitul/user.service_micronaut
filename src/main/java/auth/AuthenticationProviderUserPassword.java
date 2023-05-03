package auth;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.token.Claims;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Singleton;
import model.AuthResponse;
import model.User;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import service.UserService;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;

import java.util.Optional;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {
    private final UserService _userService;
    private final JwtTokenGenerator jwtTokenGenerator;


    public AuthenticationProviderUserPassword(UserService userService, JwtTokenGenerator jwtTokenGenerator) {
        _userService = userService;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authenticationRequest) {
        final String username = authenticationRequest.getIdentity().toString();
        final String password = authenticationRequest.getSecret().toString();

        Optional<User> userInfo = Optional.ofNullable(_userService.getUserByEmail(username));
        boolean check =  userInfo.isPresent() &&  userInfo.map(user ->user.getEmail().equals(username) && user.getPassword().equals(password)).get();
        return Flux.create(emitter -> {
            if (check) {
                String token = jwtTokenGenerator.generateToken(new Claims().set("sub", username));
                emitter.next(AuthenticationResponse.success((String) username));
                emitter.complete();
            } else {
                emitter.error(AuthenticationResponse.exception());
            }
        }, FluxSink.OverflowStrategy.ERROR);
    }
}
