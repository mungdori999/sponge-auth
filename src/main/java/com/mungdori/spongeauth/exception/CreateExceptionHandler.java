package com.mungdori.spongeauth.exception;

import com.mungdori.spongeauth.exception.error.CreateException;
import com.mungdori.spongeauth.exception.error.UpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.mungdori.spongeauth.exception.ExceptionCode.*;


@RestControllerAdvice
public class CreateExceptionHandler {


    @ExceptionHandler({CreateException.class, UpdateException.class})
    public ResponseEntity<ResponseError> handleNotFoundAccountException() {
        return new ResponseEntity<>(new ResponseError(BAD_CREATION.getCode(), BAD_CREATION.getMessage())
                , HttpStatus.BAD_REQUEST);
    }
}
