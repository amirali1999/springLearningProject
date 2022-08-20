package com.example.demo.payload.request;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;
@Getter
@Setter
public class SignupRequest {

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
