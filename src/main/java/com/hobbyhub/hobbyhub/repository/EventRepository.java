package com.hobbyhub.hobbyhub.repository;

import com.hobbyhub.hobbyhub.entity.Event;
import com.hobbyhub.hobbyhub.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByGroup(Group group);
    List<Event> findByGroupIn(Set<Group> groups);
}
