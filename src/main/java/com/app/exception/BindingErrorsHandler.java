package com.app.exception;

import com.app.form.ErrorForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BindingErrorsHandler {
    ObjectMapper mapper;

    @Before("@within(org.springframework.web.bind.annotation.RestController)")
    public void logBefore(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                handleBindingResult((BindingResult) arg);
            }
        }
    }

    private void handleBindingResult(BindingResult errors) {
        if (errors.hasErrors()) {
            List<ErrorForm> errorForms = errors.getAllErrors().stream()
                    .map(error -> new ErrorForm(((FieldError) error).getField(), error.getDefaultMessage()))
                    .collect(Collectors.toList());
            try {
                String json = mapper.writeValueAsString(errorForms);
                throw new MyBindingException(json);
            } catch (Exception e) {
                log.error("Error processing binding result", e);
                throw new RuntimeException("Error processing binding result", e);
            }
        }
    }
}