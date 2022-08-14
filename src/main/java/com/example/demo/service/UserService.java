package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.exception.AddNewObjectException;
import com.example.demo.exception.DeleteObjectException;
import com.example.demo.exception.UpdateObjectException;
import com.example.demo.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        log.info("******************* get users *******************");
        return usersRepository.findAll();
    }

//    public List<Users> getAdminUsers(){
//        return usersRepository.findAdmins();
//    }

    public void addNewUser(Users users) throws AddNewObjectException {
        if (usersRepository.equals(users)) {
            log.info("******************* add user *******************");
            throw new AddNewObjectException("the added user Exists!");
        }
        usersRepository.save(users);
    }

    public void deleteUser(Long usersID) throws DeleteObjectException {
        if (!usersRepository.existsById(usersID)) {
            throw new DeleteObjectException("the requested user not found");
        }
        log.info("******************* delete user *******************");
        usersRepository.deleteById(usersID);
    }
    @Transactional
    public void updateUser(Long usersID, String username, String name, String role, String password) throws UpdateObjectException {
        log.info("******************* update users *******************");
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
        if (password != null && password.length() > 0 && !Objects.equals(users.getPassword(), password)) {
            users.setPassword(password);
        }
    }
}
