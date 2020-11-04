package com.borris.nn.auth.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.borris.nn.auth.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
        User findByUsername(String username);
}

