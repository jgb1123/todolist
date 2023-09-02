package com.solo.todolist.status.repository;

import com.solo.todolist.member.entity.Member;
import com.solo.todolist.status.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Page<Status> findAllByMember(Member member, Pageable pageable);
}
