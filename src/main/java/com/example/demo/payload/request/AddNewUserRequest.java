package com.example.demo.payload.request;

import com.example.demo.customValidator.RoleValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class AddNewUserRequest {
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @RoleValidation
    private String role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
