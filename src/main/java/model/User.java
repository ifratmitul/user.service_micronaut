package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private int id;
    private String name;
    private String mobileNumber;
    private String email;
}
