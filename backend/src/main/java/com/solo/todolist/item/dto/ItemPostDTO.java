package com.solo.todolist.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPostDTO {

    @NotBlank
    private String title;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime targetTime;

}
