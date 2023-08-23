package com.solo.todolist.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    ITEM_NOT_FOUND(404, "Item not found");

    private final int status;

    private final String message;

    ExceptionCode(int code, String message){
        this.status = code;
        this.message = message;
    }
}
