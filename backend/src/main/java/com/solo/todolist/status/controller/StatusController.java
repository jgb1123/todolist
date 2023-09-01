package com.solo.todolist.status.controller;

import com.solo.todolist.dto.SingleResponseDTO;
import com.solo.todolist.status.dto.StatusPathDTO;
import com.solo.todolist.status.dto.StatusPostDTO;
import com.solo.todolist.status.dto.StatusResponseDTO;
import com.solo.todolist.status.entity.Status;
import com.solo.todolist.status.mapper.StatusMapper;
import com.solo.todolist.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/status")
public class StatusController {
    private final StatusMapper statusMapper;
    private final StatusService statusService;

    @PostMapping("/create")
    public ResponseEntity<SingleResponseDTO<StatusResponseDTO>> postStatus(@RequestBody StatusPostDTO statusPostDTO) {
        Status status = statusMapper.statusPostDTOToStatus(statusPostDTO);
        Status savedStatus = statusService.createStatus(status);
        StatusResponseDTO statusResponseDTO = statusMapper.statusToStatusResponseDTO(savedStatus);
        return new ResponseEntity<>(new SingleResponseDTO<>(statusResponseDTO), HttpStatus.CREATED);
    }

    @GetMapping("/find/{statusId}")
    public ResponseEntity<SingleResponseDTO<StatusResponseDTO>> getItem(@PathVariable Long statusId) {
        Status foundStatus = statusService.findStatus(statusId);
        StatusResponseDTO statusResponseDTO = statusMapper.statusToStatusResponseDTO(foundStatus);
        return new ResponseEntity<>(new SingleResponseDTO<>(statusResponseDTO), HttpStatus.OK);
    }

    @PostMapping("/update/{statusId}")
    public ResponseEntity<SingleResponseDTO<StatusResponseDTO>> patchStatus(@PathVariable Long statusId,
                                                                            @RequestBody StatusPathDTO statusPathDTO) {
        Status status = statusMapper.statusPatchDTOToStatus(statusPathDTO);
        Status updatedStatus = statusService.updateStatus(statusId, status);
        StatusResponseDTO statusResponseDTO = statusMapper.statusToStatusResponseDTO(updatedStatus);
        return new ResponseEntity<>(new SingleResponseDTO<>(statusResponseDTO), HttpStatus.OK);
    }
}
