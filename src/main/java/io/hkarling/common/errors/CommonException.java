package io.hkarling.common.errors;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final Integer code;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

}
