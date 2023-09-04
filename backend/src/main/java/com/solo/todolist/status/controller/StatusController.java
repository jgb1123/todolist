package com.solo.todolist.status.controller;

import com.solo.todolist.dto.MultiResponseDTO;
import com.solo.todolist.dto.SingleResponseDTO;
import com.solo.todolist.status.dto.*;
import com.solo.todolist.status.entity.Status;
import com.solo.todolist.status.mapper.StatusMapper;
import com.solo.todolist.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/status")
public class StatusController {
    private final StatusMapper statusMapper;
    private final StatusService statusService;

    @PostMapping("/create")
    public ResponseEntity<SingleResponseDTO<StatusResponseDTO>> postStatus(@RequestBody StatusPostDTO statusPostDTO,
                                                                           @AuthenticationPrincipal String email) {
        Status status = statusMapper.statusPostDTOToStatus(statusPostDTO);
        Status savedStatus = statusService.createStatus(status, email);
        StatusResponseDTO statusResponseDTO = statusMapper.statusToStatusResponseDTO(savedStatus);
        return new ResponseEntity<>(new SingleResponseDTO<>(statusResponseDTO), HttpStatus.CREATED);
    }

    @GetMapping("/find/{statusId}")
    public ResponseEntity<SingleResponseDTO<StatusResponseDTO>> getItem(@PathVariable Long statusId) {
        Status foundStatus = statusService.findStatus(statusId);
        StatusResponseDTO statusResponseDTO = statusMapper.statusToStatusResponseDTO(foundStatus);
        return new ResponseEntity<>(new SingleResponseDTO<>(statusResponseDTO), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<MultiResponseDTO<StatusResponseDTO>> getStatuses(@AuthenticationPrincipal String email,
                                                                           @PageableDefault(page = 1, size = 10, sort = "priority", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Status> statusPage = statusService.findStatuses(email, pageable);
        List<Status> statuses = statusPage.getContent();
        List<StatusResponseDTO> statusResponseDTOs = statusMapper.statusesToStatusResponseDTOs(statuses);
        return new ResponseEntity<>(new MultiResponseDTO<>(statusResponseDTOs, statusPage), HttpStatus.OK);
    }

    @GetMapping("/find/items")
    public ResponseEntity<MultiResponseDTO<StatusItemsResponseDTO>> getStatusesAndItems(@AuthenticationPrincipal String email,
                                                                                        @PageableDefault(page = 1, size = 10, sort = "priority", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Status> statusPage = statusService.findStatuses(email, pageable);
        List<Status> statuses = statusPage.getContent();
        List<StatusItemsResponseDTO> statusItemsResponseDTOs = statusMapper.statusesToStatusItemsResponseDTOs(statuses);
        return new ResponseEntity<>(new MultiResponseDTO<>(statusItemsResponseDTOs, statusPage), HttpStatus.OK);
    }

    @PostMapping("/update/{statusId}")
    public ResponseEntity<SingleResponseDTO<StatusResponseDTO>> patchStatus(@PathVariable Long statusId,
                                                                            @RequestBody StatusPatchDTO statusPathDTO) {
        Status status = statusMapper.statusPatchDTOToStatus(statusPathDTO);
        Status updatedStatus = statusService.updateStatus(statusId, status);
        StatusResponseDTO statusResponseDTO = statusMapper.statusToStatusResponseDTO(updatedStatus);
        return new ResponseEntity<>(new SingleResponseDTO<>(statusResponseDTO), HttpStatus.OK);
    }

    @PostMapping("/swap/{fromStatusId}/{toStatusId}")
    public ResponseEntity<HttpStatus> swapStatus(@PathVariable Long fromStatusId,
                                             @PathVariable Long toStatusId) {
        statusService.changePriority(fromStatusId, toStatusId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> deleteStatus(@RequestBody StatusDeleteDTO statusDeleteDTO,
                                                   @AuthenticationPrincipal String email) {
        statusService.deleteStatus(statusDeleteDTO.getStatusName(), email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
