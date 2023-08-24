package com.solo.todolist.item.controller;

import com.solo.todolist.dto.MultiResponseDTO;
import com.solo.todolist.dto.SingleResponseDTO;
import com.solo.todolist.item.dto.ItemPatchDTO;
import com.solo.todolist.item.dto.ItemPostDTO;
import com.solo.todolist.item.dto.ItemResponseDTO;
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
    public ResponseEntity<?> postItem(@RequestBody ItemPostDTO itemPostDto) {
        Item item = itemMapper.itemPostDTOToItem(itemPostDto);
        Item savedItem = itemService.createItem(item, itemPostDto.getMemberId());
        ItemResponseDTO itemResponseDto = itemMapper.itemToItemResponseDTO(savedItem);
        return new ResponseEntity<>(new SingleResponseDTO<>(itemResponseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable Long itemId) {
        Item foundItem = itemService.findItem(itemId);
        ItemResponseDTO itemResponseDto = itemMapper.itemToItemResponseDTO(foundItem);
        return new ResponseEntity<>(new SingleResponseDTO<>(itemResponseDto), HttpStatus.OK);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getItems(@PathVariable Long memberId,
                                      @PageableDefault(page = 1, size = 10, sort = "targetTime", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Item> itemPage = itemService.findItems(memberId, pageable);
        List<Item> items = itemPage.getContent();
        List<ItemResponseDTO> itemResponseDtos = itemMapper.itemsToItemResponseDTOs(items);
        return new ResponseEntity<>(new MultiResponseDTO<>(itemResponseDtos, itemPage), HttpStatus.OK);
    }

    @PostMapping("/update/{itemId}")
    public ResponseEntity<?> patchItem(@PathVariable Long itemId,
                                        @RequestBody ItemPatchDTO itemPatchDto) {
        Item item = itemMapper.itemPatchDTOToItem(itemPatchDto);
        Item updatedItem = itemService.updateItem(itemId, item);
        ItemResponseDTO itemResponseDto = itemMapper.itemToItemResponseDTO(updatedItem);
        return new ResponseEntity<>(new SingleResponseDTO<>(itemResponseDto), HttpStatus.OK);
    }

    @PostMapping("/delete/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
