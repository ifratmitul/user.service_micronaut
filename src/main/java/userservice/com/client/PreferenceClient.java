package userservice.com.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.Optional;

@Client("http://localhost:8081/api/preference")
public interface PreferenceClient {

    @Get("/{userId}")
    Optional<Preference> getUserPreference(Integer userId);
}
