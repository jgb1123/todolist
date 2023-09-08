package com.solo.todolist.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    LOGIN_FAILED(409, "Login failed"),
    ITEM_NOT_FOUND(404, "Item not found"),
    STATUS_NOT_FOUND(404, "Status not found"),
    STATUS_EXISTS(409, "Status exists"),
    STATUS_CANNOT_DELETE(409, "Status cannot delete"),
    ROLE_CANNOT_CHANGE(409, "Role cannot change");
    private final int status;

    private final String message;

    ExceptionCode(int code, String message){
        this.status = code;
        this.message = message;
    }
}
