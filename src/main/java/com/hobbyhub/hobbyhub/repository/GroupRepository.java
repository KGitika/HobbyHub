package com.hobbyhub.hobbyhub.repository;

import com.hobbyhub.hobbyhub.entity.Group;
import com.hobbyhub.hobbyhub.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByHobby(Hobby hobby);
}