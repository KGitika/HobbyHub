package com.hobbyhub.hobbyhub.repository;

import com.hobbyhub.hobbyhub.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    Optional<Hobby> findByName(String name);
}
