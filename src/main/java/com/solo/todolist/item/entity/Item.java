package com.solo.todolist.item.entity;

import com.solo.todolist.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(
        name = "ITEM_SEQ_GENERATOR",
        sequenceName = "ITEM_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ_GENERATOR")
    private Long itemId;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime targetTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

//    private String status;

    public void changeItemInfo(Item item) {
        if(item.getTitle() != null) this.title = item.getTitle();
        if(item.getContent() != null) this.content = item.getContent();
        if(item.getTargetTime() != null) this.targetTime = item.getTargetTime();
    }

    public void changeMember(Member member) {
        if(this.member != null) {
            this.member.getItems().remove(this);
        }
        this.member = member;
        if(!member.getItems().contains(this)) {
            member.addItem(this);
        }
    }
}
