package br.com.fsferreira.productapi.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenericUserException.class)
    public ResponseEntity<?> handleUserException(GenericUserException userException){
        var details = new Exception();
        details.setStatus(HttpStatus.BAD_REQUEST.value());
        details.setMessage(userException.getMessage());

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericServerException.class)
    public ResponseEntity<?> handleServerException(GenericServerException serverException){
        var details = new Exception();
        details.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        details.setMessage(serverException.getMessage());

        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(GenericNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(GenericNotFoundException notFoundException){
        var details = new Exception();
        details.setStatus(HttpStatus.NOT_FOUND.value());
        details.setMessage(notFoundException.getMessage());

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }
}
