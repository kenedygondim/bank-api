package com.project.bank.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @Autowired
    private MessageSource messageSource;
    //headers
    private HttpHeaders headers()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ResponseError responseError(String message, HttpStatus statusCode)
    {
        ResponseError responseError = new ResponseError();
        responseError.setStatus("error");
        responseError.setStatusCode(statusCode.value());
        responseError.setError(message);
        return responseError;
    }

    @ExceptionHandler({Exception.class})
    private ResponseEntity<Object> handleGeneral(Exception ex, WebRequest req)
    {
        if(ex.getClass().isAssignableFrom(UndeclaredThrowableException.class))
        {
            UndeclaredThrowableException undeclaredThrowableException = (UndeclaredThrowableException) ex;
            return handleGeneral((Exception) undeclaredThrowableException.getUndeclaredThrowable(), req);
        }
        else
        {
            String message = messageSource.getMessage("error.server", new Object[]{ex.getMessage()}, null);
            ResponseError responseError = responseError(message, HttpStatus.INTERNAL_SERVER_ERROR);
            return handleExceptionInternal(ex, responseError, headers(), HttpStatus.INTERNAL_SERVER_ERROR, req);
        }
    }


}
