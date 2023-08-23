package com.solo.todolist.item.mapper;

import com.solo.todolist.item.dto.ItemPatchDto;
import com.solo.todolist.item.dto.ItemPostDto;
import com.solo.todolist.item.dto.ItemResponseDto;
import com.solo.todolist.item.entity.Item;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class ItemMapper {

    public Item itemPostDtoToItem(ItemPostDto itemPostDto) {
        return Item.builder()
                .title(itemPostDto.getTitle())
                .content(itemPostDto.getContent())
                .targetTime(itemPostDto.getTargetTime())
                .build();
    }

    public Item itemPatchDtoToItem(ItemPatchDto itemPatchDto) {
        return Item.builder()
                .title(itemPatchDto.getTitle())
                .content(itemPatchDto.getContent())
                .targetTime(itemPatchDto.getTargetTime())
                .build();
    }

    public ItemResponseDto itemToItemResponseDto(Item item) {
        return ItemResponseDto.builder()
                .itemId(item.getItemId())
                .title(item.getTitle())
                .content(item.getContent())
                .targetTime(item.getTargetTime())
                .build();
    }

    public List<ItemResponseDto> itemsToItemResponseDtos(List<Item> items) {
        return items
                .stream()
                .map(this::itemToItemResponseDto)
                .collect(Collectors.toList());
    }
}
