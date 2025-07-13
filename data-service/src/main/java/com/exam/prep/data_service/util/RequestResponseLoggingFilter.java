package com.exam.prep.data_service.util;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class RequestResponseLoggingFilter implements Filter {
    private static final Logger logger = Logger.getLogger(RequestResponseLoggingFilter.class.getName());

    @Override
    public void doFilter(jakarta.servlet.ServletRequest servletRequest, jakarta.servlet.ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        logger.info("Request URI: " + request.getRequestURI());
        logger.info("Request Method: " + request.getMethod());
        logger.info("Request Headers: " + request.getHeaderNames());

        filterChain.doFilter(servletRequest, servletResponse);

        logger.info("Response Headers: " + response.getHeaderNames());
        logger.info("Response Status: " + response.getStatus());
    }
}
