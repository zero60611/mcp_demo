package com.wh.mcp_demo.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        }
        finally {
            logRequest(requestWrapper);
            logResponse(responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String payload = getPayload(request.getContentAsByteArray(), request.getCharacterEncoding());
        payload = maskSensitive(payload);

        Map<String, String> headers = Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(h -> h, h -> maskHeader(h, request.getHeader(h)), (a, b) -> b, LinkedHashMap::new));

        log.info("Incoming request: method={}, uri={}{}{}, headers={}, body={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString() != null ? "?" : "",
                request.getQueryString() != null ? request.getQueryString() : "",
                headers,
                payload);
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        String payload = getPayload(response.getContentAsByteArray(), response.getCharacterEncoding());
        payload = maskSensitive(payload);

        Map<String, String> headers = response.getHeaderNames().stream()
                .collect(Collectors.toMap(h -> h, h -> maskHeader(h, response.getHeader(h)), (a, b) -> b, LinkedHashMap::new));

        log.info("Outgoing response: status={}, headers={}, body={}",
                response.getStatus(),
                headers,
                payload);
    }

    private String maskHeader(String name, String value) {
        if (value == null) {
            return null;
        }
        String lower = name.toLowerCase();
        if (lower.contains("authorization") || lower.contains("cookie") || lower.contains("token")) {
            return "***";
        }
        return value;
    }

    private String maskSensitive(String input) {
        if (input == null) {
            return null;
        }
        return input
                .replaceAll("(?i)(\\"password\\"\\s*:\\s*\\").*?(\\")", "$1***$2")
                .replaceAll("(?i)(\\"token\\"\\s*:\\s*\\").*?(\\")", "$1***$2");
    }

    private String getPayload(byte[] buf, String encoding) {
        if (buf.length == 0) {
            return "";
        }
        try {
            return new String(buf, encoding);
        }
        catch (UnsupportedEncodingException ex) {
            return "[unknown]";
        }
    }
}

