package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.exception.type.AddNewObjectException;
import com.example.demo.exception.type.DeleteObjectException;
import com.example.demo.exception.type.UpdateObjectException;
import com.example.demo.payload.request.AddNewUserRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.request.UsersRequest;
import com.example.demo.response.Response;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<Map<String, String>> getUsers() {
        List<Users> rslt = userService.getUsers();
        return Response.generateResponseUser(rslt);
    }

    @PostMapping
    public ResponseEntity<?> addNewUser(@Valid @RequestBody AddNewUserRequest users) throws AddNewObjectException {
        return userService.addNewUser(users);
    }

    @DeleteMapping(path = "/delete/{username}")
    public String deleteUser(@PathVariable("username") String userName) throws DeleteObjectException {
        return userService.deleteUser(userName);
    }

    @PutMapping(path = "{usersName}")
    public ResponseEntity<?> updateUser(@PathVariable("usersName") String usersName,@RequestBody UsersRequest usersRequest) throws UpdateObjectException {
        return userService.updateUser(usersName, usersRequest);
    }
}
