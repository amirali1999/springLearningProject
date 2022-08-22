package com.example.demo.repositories;

import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    //    @Query(value = "SELECT s FROM Users s WHERE s.role='user'")
//    List<Users> findAdmins();
    Optional<Users> findByUsername(String username);

    @Query(value = "SELECT s FROM Users s WHERE s.username =:#{#username}")
    Optional<Users> findUsers(@Param("username") String usersname);
    @Query(value = "SELECT s FROM Users s WHERE s.username=:#{#username}")
    Optional<Users> existsByUsername(@Param("username") String username);
    @Modifying/*Whenever you are trying to modify a record in db, you have to mark it as @Modifying*/
    @Query(value = "DELETE FROM Users s WHERE s.username=:#{#username}")
    void deleteUser(@Param("username") String username);
}
