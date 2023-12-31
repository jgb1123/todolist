package com.solo.todolist.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponseDTO {

    private Long itemId;

    private String title;

    private String content;

    private LocalDateTime targetTime;

    private String statusName;
}
