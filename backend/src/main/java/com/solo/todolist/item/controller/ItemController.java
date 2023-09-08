package com.solo.todolist.item.controller;

import com.solo.todolist.common.dto.MultiResponseDTO;
import com.solo.todolist.common.dto.SingleResponseDTO;
import com.solo.todolist.item.dto.ItemPatchDTO;
import com.solo.todolist.item.dto.ItemPostDTO;
import com.solo.todolist.item.dto.ItemResponseDTO;
import com.solo.todolist.item.entity.Item;
import com.solo.todolist.item.mapper.ItemMapper;
import com.solo.todolist.item.service.ItemService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity<SingleResponseDTO<ItemResponseDTO>> postItem(@Valid @RequestBody ItemPostDTO itemPostDTO,
                                      @AuthenticationPrincipal String email) {
        Item item = itemMapper.itemPostDTOToItem(itemPostDTO);
        Item savedItem = itemService.createItem(item, email, itemPostDTO.getStatusName());
        ItemResponseDTO itemResponseDTO = itemMapper.itemToItemResponseDTO(savedItem);
        return new ResponseEntity<>(new SingleResponseDTO<>(itemResponseDTO), HttpStatus.CREATED);
    }

    @GetMapping("/find/{itemId}")
    public ResponseEntity<SingleResponseDTO<ItemResponseDTO>> getItem(@PathVariable Long itemId) {
        Item foundItem = itemService.findItem(itemId);
        ItemResponseDTO itemResponseDTO = itemMapper.itemToItemResponseDTO(foundItem);
        return new ResponseEntity<>(new SingleResponseDTO<>(itemResponseDTO), HttpStatus.OK);
    }

    @PostMapping("/update/{itemId}")
    public ResponseEntity<SingleResponseDTO<ItemResponseDTO>> patchItem(@PathVariable Long itemId,
                                        @RequestBody ItemPatchDTO itemPatchDTO) {
        Item item = itemMapper.itemPatchDTOToItem(itemPatchDTO);
        Item updatedItem = itemService.updateItem(itemId, item, itemPatchDTO.getStatusName());
        ItemResponseDTO itemResponseDTO = itemMapper.itemToItemResponseDTO(updatedItem);
        return new ResponseEntity<>(new SingleResponseDTO<>(itemResponseDTO), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<MultiResponseDTO<ItemResponseDTO>> getItems(@AuthenticationPrincipal String email,
                                      @PageableDefault(page = 1, size = 1000, sort = "targetTime", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Item> itemPage = itemService.findItems(email, pageable);
        List<Item> items = itemPage.getContent();
        List<ItemResponseDTO> itemResponseDTOs = itemMapper.itemsToItemResponseDTOs(items);
        return new ResponseEntity<>(new MultiResponseDTO<>(itemResponseDTOs, itemPage), HttpStatus.OK);
    }

    @PostMapping("/delete/{itemId}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
