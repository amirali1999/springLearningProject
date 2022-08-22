package com.example.demo.service;

import com.example.demo.document.Log;
import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.exception.type.SignUpException;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repositories.LogRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UsersRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final LogRepository logRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager,
                       UsersRepository usersRepository,
                       RoleRepository roleRepository,
                       LogRepository logRepository,
                       PasswordEncoder encoder,
                       JwtUtils jwtUtils){
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.logRepository = logRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        log.info("login-------->username:"+loginRequest.getUsername());
        logRepository.save(new Log(LocalDateTime.now().toString(),"login->username:"+loginRequest.getUsername()));
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                roles));
    }
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest){
        if (usersRepository.existsByUsername(signUpRequest.getUsername()).isPresent()) {
            log.error("user "+signUpRequest.getUsername()+" is existed!");
            logRepository.save(new Log(LocalDateTime.now().toString(),"user "+signUpRequest.getUsername()+" is existed!"));
            throw new SignUpException("user "+signUpRequest.getUsername()+" is existed!");
        }

        Users users = new Users(signUpRequest.getName(),signUpRequest.getUsername(),encoder.encode(signUpRequest.getPassword()));
        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findByName(ERole.ROLE_USER).get());
        users.setRoles(role);

        usersRepository.save(users);

        log.info("user "+signUpRequest.getUsername()+" registered successfully!!");
        logRepository.save(new Log(LocalDateTime.now().toString(),"signup - user:"+signUpRequest.getUsername()));
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
