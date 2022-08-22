package com.example.demo.payload.request;

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

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
