package com.solo.todolist.item.controller;

import com.solo.todolist.dto.MultiResponseDto;
import com.solo.todolist.dto.SingleResponseDto;
import com.solo.todolist.item.dto.ItemPatchDto;
import com.solo.todolist.item.dto.ItemPostDto;
import com.solo.todolist.item.dto.ItemResponseDto;
import com.solo.todolist.item.entity.Item;
import com.solo.todolist.item.mapper.ItemMapper;
import com.solo.todolist.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/item")
public class ItemController {
    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<?> postItem(@RequestBody ItemPostDto itemPostDto) {
        Item item = itemMapper.itemPostDtoToItem(itemPostDto);
        Item savedItem = itemService.createItem(item, itemPostDto.getMemberId());
        ItemResponseDto itemResponseDto = itemMapper.itemToItemResponseDto(savedItem);
        return new ResponseEntity<>(new SingleResponseDto<>(itemResponseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable Long itemId) {
        Item foundItem = itemService.findItem(itemId);
        ItemResponseDto itemResponseDto = itemMapper.itemToItemResponseDto(foundItem);
        return new ResponseEntity<>(new SingleResponseDto<>(itemResponseDto), HttpStatus.OK);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getItems(@PathVariable Long memberId,
                                      @PageableDefault(page = 1, size = 10, sort = "targetTime", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Item> itemPage = itemService.findItems(memberId, pageable);
        List<Item> items = itemPage.getContent();
        List<ItemResponseDto> itemResponseDtos = itemMapper.itemsToItemResponseDtos(items);
        return new ResponseEntity<>(new MultiResponseDto<>(itemResponseDtos, itemPage), HttpStatus.OK);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<?> patchItem(@PathVariable Long itemId,
                                        @RequestBody ItemPatchDto itemPatchDto) {
        Item item = itemMapper.itemPatchDtoToItem(itemPatchDto);
        Item updatedItem = itemService.updateItem(itemId, item);
        ItemResponseDto itemResponseDto = itemMapper.itemToItemResponseDto(updatedItem);
        return new ResponseEntity<>(new SingleResponseDto<>(itemResponseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
