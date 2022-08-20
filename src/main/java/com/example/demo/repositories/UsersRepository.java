package com.example.demo.repositories;

import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    //    @Query(value = "SELECT s FROM Users s WHERE s.role='user'")
//    List<Users> findAdmins();
    Optional<Users> findByUsername(String username);

    @Query(value = "SELECT s FROM Users s WHERE s.id=:#{#users_id}")
    Users findUser(@Param("users_id") long usersId);
    @Query(value = "SELECT s FROM Users s WHERE s.username=:#{#username}")
    Optional<Users> existsByUsername(@Param("username") String username);
}
