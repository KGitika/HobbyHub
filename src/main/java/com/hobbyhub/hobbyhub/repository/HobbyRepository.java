package com.hobbyhub.hobbyhub.repository;

import com.hobbyhub.hobbyhub.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
}
