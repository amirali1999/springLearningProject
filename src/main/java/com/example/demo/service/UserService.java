package com.example.demo.service;

import com.example.demo.document.Log;
import com.example.demo.entity.Users;
import com.example.demo.exception.AddNewObjectException;
import com.example.demo.exception.DeleteObjectException;
import com.example.demo.exception.UpdateObjectException;
import com.example.demo.repositories.LogRepository;
import com.example.demo.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService{
    private final UsersRepository usersRepository;
    private final LogRepository logRepository;

    @Autowired
    public UserService(UsersRepository usersRepository, LogRepository logRepository) {
        this.usersRepository = usersRepository;
        this.logRepository = logRepository;
    }
    public List<Users> getUsers() {
        log.info("******************* get users *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"get users"));

        return usersRepository.findAll();
    }

    public void addNewUser(Users users) throws AddNewObjectException {
        if (usersRepository.equals(users)) {
            log.info("******************* add user *******************");
            logRepository.save(new Log(LocalDateTime.now().toString(),"add users"));

            throw new AddNewObjectException("the added user Exists!");
        }
        usersRepository.save(users);
    }

    public void deleteUser(Long usersID) throws DeleteObjectException {
        if (!usersRepository.existsById(usersID)) {
            throw new DeleteObjectException("the requested user not found");
        }
        log.info("******************* delete user *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"delete users"));

        usersRepository.deleteById(usersID);
    }
//    @Transactional
    public void updateUser(Long usersID, String username, String name, String role, String password) throws UpdateObjectException {
        log.info("******************* update users *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"update users"));

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
