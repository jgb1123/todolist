package com.solo.todolist.status.mapper;

import com.solo.todolist.status.dto.StatusPatchDTO;
import com.solo.todolist.status.dto.StatusPostDTO;
import com.solo.todolist.status.dto.StatusResponseDTO;
import com.solo.todolist.status.entity.Status;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatusMapper {
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

    public List<StatusResponseDTO> statusesToStatusResponseDTOs(List<Status> statuses) {
        return statuses
                .stream()
                .map(this::statusToStatusResponseDTO)
                .collect(Collectors.toList());
    }
}
