package gr.publicsoft.springbootcrud.logging;

import gr.publicsoft.springbootcrud.logging.api.RequestLogger;
import gr.publicsoft.springbootcrud.logging.api.ResponseLogger;
import gr.publicsoft.springbootcrud.logging.model.RequestLog;
import gr.publicsoft.springbootcrud.logging.model.ResponseLog;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;

@Component
@Slf4j
public class RequestResponseLogger extends OncePerRequestFilter implements RequestLogger, ResponseLogger {

    private static final String COMMA_SEPARATOR = ",";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestCacheWrapper = new ContentCachingRequestWrapper(request);
        var responseCacheWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestCacheWrapper, responseCacheWrapper);

        var requestLog = createLogRequest(request, requestCacheWrapper);
        requestLog.setBody(!isNull(request.getCharacterEncoding()) ? getBodyFromBytes(requestCacheWrapper.getContentAsByteArray()) : "-");
        logRequest(requestLog);
        var logResponse = createLogResponse(response, responseCacheWrapper);
        logResponse(logResponse);

        responseCacheWrapper.copyBodyToResponse();
    }

    @Override
    public Logger getLogger() {
        return log;
    }


    /**
     * Creates a {@link RequestLog} from a given request
     */
    private RequestLog createLogRequest(HttpServletRequest request, ContentCachingRequestWrapper requestCacheWrapper) {
        return RequestLog
                .builder()
                .method(requestCacheWrapper.getMethod())
                .uri(requestCacheWrapper.getRequestURI())
                .parameters(Collections.list(requestCacheWrapper.getParameterNames())
                        .parallelStream()
                        .map(e -> String.format("\"%s\":\"%s\"", e, requestCacheWrapper.getParameter(e)))
                        .collect(Collectors.joining(COMMA_SEPARATOR)))
                .headers(Collections.list(request.getHeaderNames())
                        .parallelStream()
                        .map(e -> String.format("\"%s\":\"%s\"", e, request.getHeader(e)))
                        .collect(Collectors.joining(COMMA_SEPARATOR)))
                .build();
    }

    /**
     * Creates a {@link ResponseLog} from a given response
     */
    private ResponseLog createLogResponse(HttpServletResponse response, ContentCachingResponseWrapper responseCacheWrapper) {
        return ResponseLog
                .builder()
                .statusCode(responseCacheWrapper.getStatus())
                .headers(response.getHeaderNames()
                        .parallelStream()
                        .map(e -> String.format("\"%s\":\"%s\"", e, response.getHeader(e)))
                        .collect(Collectors.joining(COMMA_SEPARATOR)))
                .body(getBodyFromBytes(responseCacheWrapper.getContentAsByteArray()))
                .build();
    }

    private String getBodyFromBytes(byte[] bytes) {
        return isNull(bytes) ? null : new String(bytes, UTF_8).replaceAll("\\r?\\n", "");
    }
}
