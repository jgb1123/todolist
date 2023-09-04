package com.solo.todolist.status.dto;

import com.solo.todolist.item.dto.ItemResponseDTO;
import com.solo.todolist.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusItemsResponseDto {
    private Long statusId;
    private String statusName;
    private Long priority;
    private List<ItemResponseDTO> items;
}
