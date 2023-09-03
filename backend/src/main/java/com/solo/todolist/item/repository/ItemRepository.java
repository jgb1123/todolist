package com.solo.todolist.item.repository;

import com.solo.todolist.item.entity.Item;
import com.solo.todolist.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @EntityGraph(attributePaths = "status")
    Page<Item> findAllByMember(Member member, Pageable pageable);
}
