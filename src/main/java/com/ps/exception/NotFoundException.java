package com.ps.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 請求失敗，請求所希望得到的資源未被在伺服器上發現。
 * Created by samchu on 2016/10/26.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg);
    }
}
