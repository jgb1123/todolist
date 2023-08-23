package com.solo.todolist.member.entity;

import com.solo.todolist.item.entity.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private Long memberId;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>();

    public void changeMemberInfo(Member member) {
        if(member.getPassword() != null) this.password = member.getPassword();
    }

    public void addItem(Item item) {
        this.items.add(item);
        if(item.getMember() != this) {
            item.changeMember(this);
        }
    }
}
