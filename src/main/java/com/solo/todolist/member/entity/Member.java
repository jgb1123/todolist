package com.solo.todolist.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        sequenceName = "USER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    private Long memberId;

    private String email;

    private String password;

    private String name;

    public void changeInfo(Member member) {
        if(member.getPassword() != null) this.password = member.getPassword();
    }
}
