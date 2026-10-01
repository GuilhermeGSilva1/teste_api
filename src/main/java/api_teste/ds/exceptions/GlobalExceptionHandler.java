package api_teste.ds.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${server.error.include-exception:false}")
    private boolean includeException;

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleObjectNotFoundException(
            ObjectNotFoundException objectNotFoundException,
            WebRequest request) {

        return buildErrorResponse(
                objectNotFoundException,
                objectNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND,
                request
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException,
            WebRequest request) {

        return buildErrorResponse(
                dataIntegrityViolationException,
                dataIntegrityViolationException.getMessage(),
                HttpStatus.CONFLICT,
                request
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException,
            WebRequest request) {

        return buildErrorResponse(
                constraintViolationException,
                constraintViolationException.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY,
                request
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughException(
            Exception exception,
            WebRequest request) {

        return buildErrorResponse(
                exception,
                "Um erro inesperado aconteceu.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus,
            WebRequest request) {

        ErrorResponse errorResponse =
                new ErrorResponse(httpStatus.value(), message);

        if (includeException) {
            errorResponse.setStackTrace(
                    ExceptionUtils.getStackTrace(exception)
            );
        }

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
