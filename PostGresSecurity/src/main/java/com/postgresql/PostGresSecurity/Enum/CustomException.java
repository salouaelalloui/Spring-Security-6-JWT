package com.postgresql.PostGresSecurity.Enum;

public enum CustomException {
    TOKEN_NOT_FOUND("token not found in the database "),
    LOGIN_AGAIN("you need to authenticate again");
    private final String message;

    CustomException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
