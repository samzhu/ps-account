package com.ps.controller;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by samchu on 2017/4/12.
 */
@ControllerAdvice
public class ExceptionHandlingController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map methodArgumentNotValid(HttpServletRequest req, MethodArgumentNotValidException ex) {
        Map<String, Object> errorAttributes = new LinkedHashMap<String, Object>();
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("status", 400);
        errorAttributes.put("error", "Validation failed");
        errorAttributes.put("exception", ex.getClass());
        List<ObjectError> objectErrorList = ex.getBindingResult().getAllErrors();
        StringBuilder sb = new StringBuilder();
        objectErrorList.forEach(error -> sb.append((sb.length() > 0 ? ", " : "") + error.getDefaultMessage()));
        errorAttributes.put("message", sb);
        errorAttributes.put("path", req.getRequestURI());
        return errorAttributes;
    }
}
