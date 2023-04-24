package userservice.com.client;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Introspected
public class Preference {

    private int id;
    private String name;
    private int userId;

}
