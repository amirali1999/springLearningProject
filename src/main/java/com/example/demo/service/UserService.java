package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.exception.AddNewObjectException;
import com.example.demo.exception.DeleteObjectException;
import com.example.demo.exception.UpdateObjectException;
import com.example.demo.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService{
    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

//    public List<Users> getAdminUsers(){
//        return usersRepository.findAdmins();
//    }

    public void addNewUser(Users users) throws AddNewObjectException {
        if (usersRepository.equals(users)) {
            throw new AddNewObjectException("the added user Exists!");
        }
        usersRepository.save(users);
    }

    public void deleteUser(Long usersID) throws DeleteObjectException {
        if (!usersRepository.existsById(usersID)) {
            throw new DeleteObjectException("the requested user not found");
        }
        usersRepository.deleteById(usersID);
    }
    @Transactional
    public void updateUser(Long usersID, String username, String name, String role, String password) throws UpdateObjectException {
        Users users = usersRepository.findById(usersID).orElseThrow(() -> new IllegalArgumentException("can not add"));
        if (!usersRepository.existsById(usersID)) {
            throw new UpdateObjectException("user not found!");
        }
        if (username != null && username.length() > 0 && !Objects.equals(users.getUsername(), username)) {
            users.setUsername(username);
        }
        if (name != null && name.length() > 0 && !Objects.equals(users.getName(), name)) {
            users.setName(name);
        }
        //TODO
//        if (role != null && role.length() > 0 && !Objects.equals(user.getRoles(), role)) {
//            user.setRoles(roles);
//        }
        if (password != null && password.length() > 0 && !Objects.equals(users.getPassword(), password)) {
            users.setPassword(password);
        }
    }
}
