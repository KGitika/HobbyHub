package com.hobbyhub.hobbyhub.repository;

import com.hobbyhub.hobbyhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository provides CRUD methods by default
}
