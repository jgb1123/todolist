package com.solo.todolist.status.repository;

import com.solo.todolist.status.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
