package com.solo.todolist.status.service;

import com.solo.todolist.exception.BusinessLogicException;
import com.solo.todolist.exception.ExceptionCode;
import com.solo.todolist.item.entity.Item;
import com.solo.todolist.item.repository.ItemRepository;
import com.solo.todolist.item.service.ItemService;
import com.solo.todolist.member.entity.Member;
import com.solo.todolist.member.service.MemberService;
import com.solo.todolist.status.entity.Status;
import com.solo.todolist.status.repository.StatusRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;
    private final MemberService memberService;
    private final ItemRepository itemRepository;

    public Status createStatus(Status status, String email) {
        Member foundMember = memberService.getVerifiedMember(email);
        if(statusRepository.findByStatusNameAndMember(status.getStatusName(), foundMember).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.STATUS_EXISTS);
        }
        status.changeMember(foundMember);
        Status savedStore = statusRepository.save(status);
        savedStore.setFirstPriority(savedStore.getStatusId());
        return savedStore;
    }

    public Status findStatus(Long statusId) {
        return getVerifiedStatus(statusId);
    }

    public Page<Status> findStatuses(String email, Pageable pageable) {
        Member foundMember = memberService.getVerifiedMember(email);
        pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        return statusRepository.findAllByMember(foundMember, pageable);
    }

    public Status updateStatus(Long statusId, Status status) {
        Status foundStatus = getVerifiedStatus(statusId);
        foundStatus.changeStatusName(status.getStatusName());
        return foundStatus;
    }

    public void changePriority(Long fromStatusId, Long toStatusId) {
        Status fromStatus = getVerifiedStatus(fromStatusId);
        Status toStatus = getVerifiedStatus(toStatusId);
        Long fromPriority = fromStatus.getPriority();
        Long toPriority = toStatus.getPriority();
        fromStatus.changePriority(toPriority);
        toStatus.changePriority(fromPriority);
    }

    public void deleteStatus(String statusName, String email) {
        Member foundMember = memberService.getVerifiedMember(email);
        Status foundStatus = getVerifiedStatusByStatusName(statusName, foundMember);
        Status noneStatus = getVerifiedStatusByStatusName("None", foundMember);
        List<Item> foundItems = itemRepository.findAllByStatus(foundStatus);
        for (Item foundItem : foundItems) {
            foundItem.changeStatus(noneStatus);
        }
        statusRepository.delete(foundStatus);
    }

    public Status getVerifiedStatus(Long statusId) {
        return statusRepository.findById(statusId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATUS_NOT_FOUND));
    }

    public Status getVerifiedStatusByStatusName(String statusName, Member member) {
        return statusRepository.findByStatusNameAndMember(statusName, member)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATUS_NOT_FOUND));
    }
}