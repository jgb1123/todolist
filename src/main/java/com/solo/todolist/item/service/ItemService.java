package com.solo.todolist.item.service;

import com.solo.todolist.exception.BusinessLogicException;
import com.solo.todolist.exception.ExceptionCode;
import com.solo.todolist.item.entity.Item;
import com.solo.todolist.item.repository.ItemRepository;
import com.solo.todolist.member.entity.Member;
import com.solo.todolist.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberService memberService;

    public Item createItem(Item item, Long memberId) {
        Member foundMember = memberService.getVerifiedMember(memberId);
        item.changeMember(foundMember);
        return itemRepository.save(item);
    }

    public Item findItem(Long itemId) {
        return getVerifiedItem(itemId);
    }

    public Page<Item> findItems(Long memberId, Pageable pageable) {
        Member foundMember = memberService.getVerifiedMember(memberId);
        pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        return itemRepository.findAllByMember(foundMember, pageable);
    }

    public Item updateItem(Long itemId, Item item) {
        Item foundItem = getVerifiedItem(itemId);
        foundItem.changeItemInfo(item);
        return foundItem;
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public Item getVerifiedItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));
    }
}
