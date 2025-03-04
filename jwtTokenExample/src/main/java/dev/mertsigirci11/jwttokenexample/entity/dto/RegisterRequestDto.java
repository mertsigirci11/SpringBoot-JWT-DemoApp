package dev.mertsigirci11.jwttokenexample.entity.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
