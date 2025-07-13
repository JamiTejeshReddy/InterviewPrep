package com.exam.prep.data_service.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;
@Component
public class CustomInterceptor implements HandlerInterceptor {
    private static final Logger logger = Logger.getLogger(CustomInterceptor.class.getName());


    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        logger.info("Request body: {}" + request);
        return true; // Continue processing the request
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, org.springframework.web.servlet.ModelAndView modelAndView) {
        logger.info("Response body: {}" + response);
    }
}

