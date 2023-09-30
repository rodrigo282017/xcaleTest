package com.xcale.xcaletest.config;

import io.micrometer.common.lang.Nullable;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

public class TraceInterceptor implements HandlerInterceptor {

    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    @Override
    public boolean preHandle(@Nullable final HttpServletRequest request,
                             final HttpServletResponse response,
                             @Nullable final Object handler) {
        // Generate an id per trace
        String traceId = UUID.randomUUID().toString();

        // Add to MDC (Mapped Diagnostic Context)
        MDC.put(TRACE_ID_HEADER, traceId);

        // Add Trace ID to the HTTP header
        response.addHeader(TRACE_ID_HEADER, traceId);

        return true;
    }

    @Override
    public void postHandle(@Nullable final HttpServletRequest request,
                           @Nullable final HttpServletResponse response,
                           @Nullable final Object handler,
                           final ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(@Nullable final HttpServletRequest request,
                                @Nullable final HttpServletResponse response,
                                @Nullable final Object handler,
                                final Exception ex) {
        MDC.remove(TRACE_ID_HEADER);
    }
}
