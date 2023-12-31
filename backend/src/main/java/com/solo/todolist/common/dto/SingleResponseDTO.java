package com.solo.todolist.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleResponseDTO<T> {
    private T data;
}
