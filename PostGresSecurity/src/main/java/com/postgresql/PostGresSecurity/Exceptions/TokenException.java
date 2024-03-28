package com.postgresql.PostGresSecurity.Exceptions;

import com.postgresql.PostGresSecurity.Enum.CustomException;
import lombok.RequiredArgsConstructor;


public class TokenException extends RuntimeException{
    private final CustomException type;

    public TokenException(CustomException type) {
        super(type.getMessage());
        this.type = type;
    }

    public CustomException getType() {
        return type;
    }

}
