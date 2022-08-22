package com.example.demo.service;

import com.example.demo.customValidator.RoleValidation;
import com.example.demo.document.Log;
import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.exception.type.AddNewObjectException;
import com.example.demo.exception.type.DeleteObjectException;
import com.example.demo.exception.type.UpdateObjectException;
import com.example.demo.payload.request.AddNewUserRequest;
import com.example.demo.payload.request.UsersRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repositories.LogRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserService{
    private final PasswordEncoder encoder;
    private final UsersRepository usersRepository;
    private final LogRepository logRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserService(PasswordEncoder encoder,
                       UsersRepository usersRepository,
                       LogRepository logRepository,
                       RoleRepository roleRepository) {
        this.encoder = encoder;
        this.usersRepository = usersRepository;
        this.logRepository = logRepository;
        this.roleRepository = roleRepository;
    }
    public List<Users> getUsers() {
        log.info("******************* get users *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"get users"));

        return usersRepository.findAll();
    }
    public ResponseEntity<?> addNewUser(AddNewUserRequest addNewUserRequest) throws AddNewObjectException {
        log.info("******************* add user *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"add user"));
        if (usersRepository.existsByUsername(addNewUserRequest.getUsername()).isPresent()) {
            log.error("username "+addNewUserRequest.getUsername()+" exists");
            logRepository.save(new Log(LocalDateTime.now().toString(),"Error: username "+addNewUserRequest.getUsername()+" exists"));
            throw new AddNewObjectException("username "+addNewUserRequest.getUsername()+" exists");
        }
        // Create new user's account
        Users users = new Users(addNewUserRequest.getName(),addNewUserRequest.getUsername(),encoder.encode(addNewUserRequest.getPassword()));

//        Set<String> strRoles = addNewUserRequest.getRole();
        Set<String> strRoles = new HashSet<>();
        strRoles.add(addNewUserRequest.getRole());
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
            roles.add(userRole);
        } else {
            strRoles.forEach(role->{
                if(Objects.equals(role, "admin")){
                    Optional<Role> adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).or(()-> {
                        log.error("role "+addNewUserRequest.getRole()+" not found !");
                        logRepository.save(new Log(LocalDateTime.now().toString(),"Error: role "+addNewUserRequest.getRole()+" not found !"));
                        throw new AddNewObjectException("Role not found !");
                    });
                    roles.add(adminRole.get());
                }
                else if(Objects.equals(role, "user")){
                    Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER).or(()-> {
                        log.error("role "+addNewUserRequest.getRole()+" not found !");
                        logRepository.save(new Log(LocalDateTime.now().toString(),"Error: role "+addNewUserRequest.getRole()+" not found !"));
                        throw new AddNewObjectException("Role not found !");
                    });
                    roles.add(userRole.get());
                }
                else {
                    log.error("role "+addNewUserRequest.getRole()+" not َAllowed !");
                    logRepository.save(new Log(LocalDateTime.now().toString(),"Error: role "+addNewUserRequest.getRole()+" not َAllowed !"));
                    throw new AddNewObjectException("role not َAllowed !");
                }
            });
        }
        users.setRoles(roles);
        usersRepository.save(users);
        log.info("user "+addNewUserRequest.getUsername()+" registered successfully!!");
        logRepository.save(new Log(LocalDateTime.now().toString(),"signup - user:"+addNewUserRequest.getUsername()));
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @Transactional
    public String deleteUser(String userName) throws DeleteObjectException {
        if (usersRepository.existsByUsername(userName).isPresent()){
            log.info("******************* delete user *******************");
            logRepository.save(new Log(LocalDateTime.now().toString(),"delete user"+userName));
            usersRepository.deleteUser(userName);
            return "user "+userName+" delete successfully!";
        }
        else {
            log.error("user "+userName+" not found");
            logRepository.save(new Log(LocalDateTime.now().toString(),"user "+userName+" not found"));
            throw new DeleteObjectException("user "+userName+" not found");
        }
    }
    public ResponseEntity<?> updateUser(String usersName, UsersRequest usersRequest) throws UpdateObjectException {
        Users users = usersRepository.findUsers(usersName).orElseThrow(() -> new UpdateObjectException("user not found !"));

        if (!usersRequest.getUsername().equals(users.getUsername()) && usersRequest.getUsername().length() > 0) {
            if(usersRepository.findUsers(usersRequest.getUsername()).isPresent()){
                throw new UpdateObjectException("username "+usersRequest.getUsername()+" exists !");
            }
            users.setUsername(usersRequest.getUsername());
        }
        if (!usersRequest.getName().equals(users.getName()) && users.getName().length() > 0) {
            users.setName(usersRequest.getName());
        }
        if(!usersRequest.getPassword().isEmpty()){
            users.setPassword(encoder.encode(usersRequest.getPassword()));
        }

        log.info("******************* update users *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"update user"));
        usersRepository.save(users);
        return ResponseEntity.ok(new MessageResponse("User update successfully!"));
    }
}
