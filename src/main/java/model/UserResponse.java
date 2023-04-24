package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import userservice.com.client.Preference;

@Getter
@Setter
@Builder
public class UserResponse {
    private User user;
    private Preference preference;
}
