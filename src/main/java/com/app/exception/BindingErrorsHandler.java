package com.app.exception;

import com.app.form.ErrorForm;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.ArrayList;
import java.util.Arrays;
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
        Arrays.stream(joinPoint.getArgs())
                .filter(BindingResult.class::isInstance)
                .map(BindingResult.class::cast)
                .filter(BindingResult::hasErrors)
                .map(errors -> errors.getAllErrors().stream()
                        .map(it -> new ErrorForm(((FieldError) it).getField(), it.getDefaultMessage()))
                        .collect(Collectors.toList()))
                .findFirst()
                .ifPresent(errorForms -> {
                    try {
                        String json = mapper.writeValueAsString(errorForms);
                        throw new MyBindingException(json);
                    } catch (JsonProcessingException jPE) {
                        log.error(jPE.getMessage(), jPE);
                    }
                });
    }
}