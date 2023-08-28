package com.solo.todolist.item.repository;

import com.solo.todolist.item.entity.Item;
import com.solo.todolist.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAllByMember(Member member, Pageable pageable);
}
