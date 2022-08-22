package com.example.demo.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class UsersRequest {
    private String name;
    private String username;
    private Set<String> roles;
    private String password;
}
