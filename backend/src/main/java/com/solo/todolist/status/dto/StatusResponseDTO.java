package com.solo.todolist.status.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusResponseDTO {
    private Long statusId;
    private String statusName;
    private Long priority;
}
