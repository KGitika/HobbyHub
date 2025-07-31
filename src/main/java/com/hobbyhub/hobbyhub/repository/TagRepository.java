package com.hobbyhub.hobbyhub.repository;

import com.hobbyhub.hobbyhub.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
