package com.solo.todolist.item.mapper;

import com.solo.todolist.item.dto.ItemPatchDTO;
import com.solo.todolist.item.dto.ItemPostDTO;
import com.solo.todolist.item.dto.ItemResponseDTO;
import com.solo.todolist.item.entity.Item;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Data
public class ItemMapper {

    public Item itemPostDTOToItem(ItemPostDTO itemPostDTO) {
        return Item.builder()
                .title(itemPostDTO.getTitle())
                .content(itemPostDTO.getContent())
                .targetTime(itemPostDTO.getTargetTime())
                .build();
    }

    public Item itemPatchDTOToItem(ItemPatchDTO itemPatchDTO) {
        return Item.builder()
                .title(itemPatchDTO.getTitle())
                .content(itemPatchDTO.getContent())
                .targetTime(itemPatchDTO.getTargetTime())
                .build();
    }

    public ItemResponseDTO itemToItemResponseDTO(Item item) {
        return ItemResponseDTO.builder()
                .itemId(item.getItemId())
                .title(item.getTitle())
                .content(item.getContent())
                .targetTime(item.getTargetTime())
                .statusName(item.getStatus().getStatusName())
                .build();
    }

    public List<ItemResponseDTO> itemsToItemResponseDTOs(List<Item> items) {
        return items
                .stream()
                .map(this::itemToItemResponseDTO)
                .collect(Collectors.toList());
    }
}
