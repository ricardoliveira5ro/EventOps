package com.event.ops.common.security;

import com.event.ops.common.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class RateLimitingFilter implements Filter {

    private final ConcurrentMap<String, Bucket> buckets = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Bucket getBucket(String clientId) {
        return buckets.computeIfAbsent(clientId, k -> Bucket.builder()
            .addLimit(Bandwidth.builder().capacity(10).refillIntervally(1, Duration.ofSeconds(1)).build())
            .build()
        );
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String clientIp = httpRequest.getRemoteAddr();
        Bucket bucket = getBucket(clientIp);

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            httpResponse.setContentType("application/json");
            httpResponse.setStatus(429);

            ErrorResponse errorResponse = new ErrorResponse(429, "Too Many Requests", List.of("You have exceeded the allowed request limit. Please try again later."));

            String json = objectMapper.writeValueAsString(errorResponse);
            httpResponse.getWriter().write(json);
        }
    }
}