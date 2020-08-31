package gr.publicsoft.springbootcrud.logging.api;


import gr.publicsoft.springbootcrud.logging.model.RequestLog;
import org.slf4j.Logger;

import static java.util.Objects.nonNull;

public interface RequestLogger {

    Logger getLogger();

    default void logRequest(RequestLog requestLog) {
        getLogger().info(String.format("[REQUEST] %s \"%s\"%s%s%s",
                requestLog.getMethod(),
                requestLog.getUri(),
                (nonNull(requestLog.getParameters()) && !requestLog.getParameters().isEmpty()) ? String.format(" | PARAMETERS: {%s}", requestLog.getParameters()) : "",
                (nonNull(requestLog.getHeaders()) && !requestLog.getHeaders().isEmpty()) ? String.format(" | HEADERS: {%s}", requestLog.getHeaders()) : "",
                (nonNull(requestLog.getBody()) && !requestLog.getBody().isEmpty()) ? String.format(" | BODY: %s", requestLog.getBody()) : ""
        ).replaceAll("\\s{2,}", " "));
    }
}
