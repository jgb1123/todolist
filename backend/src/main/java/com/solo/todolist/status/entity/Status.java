package com.solo.todolist.status.entity;

import com.solo.todolist.item.entity.Item;
import com.solo.todolist.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "STATUS_SEQ_GENERATOR",
        sequenceName = "STATUS_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATUS_SEQ_GENERATOR")
    private Long statusId;

    @Column
    private String statusName;

    @Column
    private Long priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @BatchSize(size = 100)
    @Builder.Default
    @OneToMany(mappedBy = "status")
    private List<Item> items = new ArrayList<>();

    public void setFirstPriority(Long statusId) {
        this.priority = statusId;
    }

    public void changePriority(Long priority) {
        this.priority = priority;
    }

    public void changeStatusName(String statusName) {
        this.statusName = statusName;

    }
    public void changeMember(Member member) {
        if(this.member != null) {
            this.member.getStatuses().remove(this);
        }
        this.member = member;
        if(!member.getStatuses().contains(this)) {
            member.addStatus(this);
        }
    }

    public void addItem(Item item) {
        this.items.add(item);
        if(item.getStatus() != this) {
            item.changeStatus(this);
        }
    }


}
