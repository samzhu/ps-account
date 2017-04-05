package com.ps.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 伺服器已經理解請求，但是拒絕執行它。
 * Created by samchu on 2016/10/26.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends AppException {
    public ForbiddenException(String msg) {
        super(msg);
    }
}
