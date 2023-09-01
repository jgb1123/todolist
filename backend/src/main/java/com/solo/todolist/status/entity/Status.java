package com.solo.todolist.status.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public void setFirstPriority(Status status) {
        this.priority = status.getStatusId();
    }

    public void changePriority(Long priority) {
        this.priority = priority;
    }

    public void changeStatusName(String statusName) {
        this.statusName = statusName;
    }
}
