package com.app.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JsonToUrlEncodedAuthenticationFilter extends OncePerRequestFilter {
    static String TOKEN_PATH = "/api/token";
    static String JSON_CONTENT_TYPE = "application/json";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (isJsonTokenRequest(request)) {
            processJsonRequest(request, response, filterChain);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isJsonTokenRequest(HttpServletRequest request) {
        return TOKEN_PATH.equals(request.getServletPath())
                && request.getContentType() != null
                && request.getContentType().contains(JSON_CONTENT_TYPE);
    }

    private void processJsonRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Map<String, Object> jsonMap = new ObjectMapper().readValue(request.getInputStream(), new TypeReference<Map<String, Object>>() {
        });
        Map<String, String[]> parameters = jsonMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new String[]{e.getValue().toString()}
                ));
        filterChain.doFilter(new ParameterMapWrapper(request, parameters), response);
    }

    private static class ParameterMapWrapper extends HttpServletRequestWrapper {
        private final Map<String, String[]> params;

        ParameterMapWrapper(HttpServletRequest request, Map<String, String[]> params) {
            super(request);
            this.params = params;
        }

        @Override
        public String getParameter(String name) {
            String[] values = params.get(name);
            return values != null && values.length > 0 ? values[0] : null;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return params;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(params.keySet());
        }

        @Override
        public String[] getParameterValues(String name) {
            return params.get(name);
        }
    }
}