package com.app.exception;

import com.app.dto.ApiMessageDto;
import com.app.dto.ApiResponse;
import com.app.form.ErrorForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiMessageDto<String>> handleNotFoundException(NotFoundException ex) {
        return createApiResponse("ERROR", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(@NonNull NoHandlerFoundException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setCode("ERROR >> handleNoHandlerFoundException");
        apiMessageDto.setResult(false);
        apiMessageDto.setMessage("[Ex3]: 404 - " + ex.getMessage());
        return new ResponseEntity<>(apiMessageDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AccessDeniedException.class, ForbiddenException.class})
    public ResponseEntity<ApiMessageDto<String>> handleAccessDeniedException(Exception ex) {
        return createApiResponse("ERROR Forbidden", ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiMessageDto<List<ErrorForm>> exceptionHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        ApiMessageDto<List<ErrorForm>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setCode("ERROR");
        apiMessageDto.setResult(false);
        if (ex instanceof MyBindingException) {
            try {
                List<ErrorForm> errorForms = Arrays.asList(objectMapper.readValue(ex.getMessage(), ErrorForm[].class));
                apiMessageDto.setData(errorForms);
                apiMessageDto.setMessage("Invalid form");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            apiMessageDto.setMessage("[Ex2]: " + ex.getMessage());
        }
        return apiMessageDto;
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ApiMessageDto<String>> handleBadRequestException(BadRequestException ex) {
        return createApiResponse(ex.getCode() != null ? ex.getCode() : "ERROR-400", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizationException.class)
    public ResponseEntity<ApiMessageDto<String>> handleUnauthorizationException(UnauthorizationException ex) {
        return createApiResponse("ERROR-401", ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getPropertyPath() + " | " + violation.getMessage()
                ));
        ApiResponse<Map<String, String>> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST, "BAD REQUEST", errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExceptionHandler.class)
    public ResponseEntity<ApiMessageDto<String>> handleTokenException(TokenExceptionHandler ex) {
        return createApiResponse("Invalid token or token had expired!", ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomizeOverallException.class)
    public ResponseEntity<ApiMessageDto<String>> handleCustomizeOverallException(CustomizeOverallException ex) {
        return createApiResponse("Internal Server Error", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiMessageDto<String>> createApiResponse(String code, String message, HttpStatus status) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setCode(code);
        apiMessageDto.setResult(false);
        apiMessageDto.setMessage(message);
        return new ResponseEntity<>(apiMessageDto, status);
    }
}