package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.exception.AddNewObjectException;
import com.example.demo.exception.DeleteObjectException;
import com.example.demo.exception.UpdateObjectException;
import com.example.demo.response.Response;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void addNewUser(@RequestBody Users users) throws AddNewObjectException {
        userService.addNewUser(users);
    }

    @DeleteMapping(path = "{usersID}")
    public void deleteUser(@PathVariable("usersID") Long usersID) throws DeleteObjectException {
        userService.deleteUser(usersID);
    }

    @PutMapping(path = "{usersID}")
    public void updateUser(@PathVariable("usersID") Long usersID
            , @RequestParam(required = false) String name
            , @RequestParam(required = false) String username
            , @RequestParam(required = false) String role
            , @RequestParam(required = false) String password) throws UpdateObjectException {
        userService.updateUser(usersID, username, name, role, password);
    }
}
