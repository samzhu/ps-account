package com.ps.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 由於和被請求的資源的當前狀態之間存在衝突，請求無法完成。
 * Created by samchu on 2016/10/26.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends AppException {
    public ConflictException(String msg) {
        super(msg);
    }
}
