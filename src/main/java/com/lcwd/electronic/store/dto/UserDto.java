package com.lcwd.electronic.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {

    private String userId;

    @Size(min = 3, max = 30, message = "Invalid Name!!")
    private String name;
    @Email(message = "Invalid email!!")
    private String email;
    @NotBlank(message = "password is required!!")
    private String password;
    @Size(min = 4, max = 6, message = "Invalid gender!!")
    private String gender;
    @NotBlank(message = "write something!!")
    private String about;
    private String imageName;
}
