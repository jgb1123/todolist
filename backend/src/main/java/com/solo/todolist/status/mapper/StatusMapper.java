package com.solo.todolist.status.mapper;

import com.solo.todolist.status.dto.StatusPathDTO;
import com.solo.todolist.status.dto.StatusPostDTO;
import com.solo.todolist.status.dto.StatusResponseDTO;
import com.solo.todolist.status.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {
    public Status statusPostDTOToStatus(StatusPostDTO statusPostDTO) {
        return Status.builder()
                .statusName(statusPostDTO.getStatusName())
                .build();
    }

    public Status statusPatchDTOToStatus(StatusPathDTO statusPathDTO) {
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
}
