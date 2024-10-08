package com.app.service.impl;

import com.app.service.LoggingService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LoggingServiceImpl implements LoggingService {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void log(HttpServletRequest request, HttpServletResponse response, Object body) {
        logRequest(request, body);
        logResponse(request, response, body);
    }

    private void logRequest(HttpServletRequest request, Object body) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("method", request.getMethod());
        requestData.put("path", request.getRequestURI());
        requestData.put("headers", buildHeadersMap(request));
        requestData.put("parameters", request.getParameterMap());
        requestData.put("body", body);
        logAsJson("REQUEST", requestData);
    }

    private void logResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("method", request.getMethod());
        responseData.put("path", request.getRequestURI());
        responseData.put("responseBody", body);
        logAsJson("RESPONSE", responseData);
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        headerName -> headerName,
                        request::getHeader,
                        (v1, v2) -> v1
                ));
    }

    private void logAsJson(String prefix, Object data) {
        try {
            log.warn("{}: {}", prefix, objectMapper.writeValueAsString(data));
        } catch (Exception e) {
            log.error("Error logging as JSON", e);
        }
    }
}