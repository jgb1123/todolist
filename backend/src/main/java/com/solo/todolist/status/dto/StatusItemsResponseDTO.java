package com.solo.todolist.status.dto;

import com.solo.todolist.item.dto.ItemResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusItemsResponseDTO {
    private Long statusId;
    private String statusName;
    private Long priority;
    private List<ItemResponseDTO> items;
}
