package com.solo.todolist.item.mapper;

import com.solo.todolist.item.dto.ItemPatchDTO;
import com.solo.todolist.item.dto.ItemPostDTO;
import com.solo.todolist.item.dto.ItemResponseDTO;
import com.solo.todolist.item.entity.Item;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class ItemMapper {

    public Item itemPostDTOToItem(ItemPostDTO itemPostDto) {
        return Item.builder()
                .title(itemPostDto.getTitle())
                .content(itemPostDto.getContent())
                .targetTime(itemPostDto.getTargetTime())
                .build();
    }

    public Item itemPatchDTOToItem(ItemPatchDTO itemPatchDto) {
        return Item.builder()
                .title(itemPatchDto.getTitle())
                .content(itemPatchDto.getContent())
                .targetTime(itemPatchDto.getTargetTime())
                .build();
    }

    public ItemResponseDTO itemToItemResponseDTO(Item item) {
        return ItemResponseDTO.builder()
                .itemId(item.getItemId())
                .title(item.getTitle())
                .content(item.getContent())
                .targetTime(item.getTargetTime())
                .build();
    }

    public List<ItemResponseDTO> itemsToItemResponseDTOs(List<Item> items) {
        return items
                .stream()
                .map(this::itemToItemResponseDTO)
                .collect(Collectors.toList());
    }
}
