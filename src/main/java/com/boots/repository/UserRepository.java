package com.boots.repository;

import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByPhoneNumber(String phoneNumber);
    Optional<User> findById(Long id);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :name")
    List<User> findByRoleName(@Param("name") String roleName);
}
