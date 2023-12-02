package com.example.taskmanagament.repository;

import com.example.taskmanagament.dao.entity.User;
import com.example.taskmanagament.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findByUserRole(UserRole userRole);
    List<User> findByEmailContaining(String domain);
    boolean existsByUsername(String username);
}
