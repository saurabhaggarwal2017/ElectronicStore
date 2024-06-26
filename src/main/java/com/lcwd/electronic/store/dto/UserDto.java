package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.entities.UserRoles;
import com.lcwd.electronic.store.validate.ImageNameValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    //    @Email(message = "Invalid email!!")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Invalid email!")
    private String email;

    @NotBlank(message = "password is required!!")
    private String password;
    @Size(min = 4, max = 6, message = "Invalid gender!!")
    private String gender;
    @NotBlank(message = "write something!!")
    private String about;

    @ImageNameValid
    private String imageName;
    private Set<UserRoles> userRoles = new HashSet<>();
}
