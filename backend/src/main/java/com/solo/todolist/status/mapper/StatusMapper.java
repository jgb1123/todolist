package com.solo.todolist.status.mapper;

import com.solo.todolist.item.dto.ItemResponseDTO;
import com.solo.todolist.item.mapper.ItemMapper;
import com.solo.todolist.status.dto.StatusItemsResponseDTO;
import com.solo.todolist.status.dto.StatusPatchDTO;
import com.solo.todolist.status.dto.StatusPostDTO;
import com.solo.todolist.status.dto.StatusResponseDTO;
import com.solo.todolist.status.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StatusMapper {
    private final ItemMapper itemMapper;

    public Status statusPostDTOToStatus(StatusPostDTO statusPostDTO) {
        return Status.builder()
                .statusName(statusPostDTO.getStatusName())
                .build();
    }

    public Status statusPatchDTOToStatus(StatusPatchDTO statusPathDTO) {
        return Status.builder()
                .priority(statusPathDTO.getPriority())
                .build();
    }

    public StatusResponseDTO statusToStatusResponseDTO(Status status) {
        return StatusResponseDTO.builder()
                .statusId(status.getStatusId())
                .statusName(status.getStatusName())
                .priority(status.getPriority())
                .build();
    }

    public StatusItemsResponseDTO statusToStatusItemsResponseDto(Status status) {
        List<ItemResponseDTO> itemResponseDTOs = getSortedItemResponseDTOsByTargetTime(status);
        return StatusItemsResponseDTO.builder()
                .statusId(status.getStatusId())
                .statusName(status.getStatusName())
                .priority(status.getPriority())
                .items(itemResponseDTOs)
                .build();
    }

    public List<StatusItemsResponseDTO> statusesToStatusItemsResponseDTOs(List<Status> statuses) {
        return statuses
                .stream()
                .map(this::statusToStatusItemsResponseDto)
                .toList();
    }

    private List<ItemResponseDTO> getSortedItemResponseDTOsByTargetTime(Status status) {
        List<ItemResponseDTO> itemResponseDTOs = itemMapper.itemsToItemResponseDTOs(status.getItems());
        itemResponseDTOs.sort(Comparator.comparing(ItemResponseDTO::getTargetTime));
        return itemResponseDTOs;
    }
}
